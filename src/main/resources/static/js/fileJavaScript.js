//提示框初始化
toastr.options = {
    positionClass: "toast-bottom-center",
    timeOut: "3000",
};

window.onload = function () {
    document.getElementById("goBackButton").disabled = true;
    refreshData();
};

//刷新页面数据
function refreshData() {
    $("#fileList").empty("");
    $.ajax({
        type: "get",
        url: "/File/refreshData",
        success: function (data) {
            $(data).each(
                function (i, oneFile) {
                    $("#fileList").append(
                        "<tr>" +
                        "<td><a onclick='clickFile(\"" + oneFile.fileId + "\",\"" + oneFile.fileType + "\")'>" + oneFile.fileName + "</a></td>" +
                        "<td>" + oneFile.updateTime + "</td>" +
                        "<td>" + oneFile.fileType + "</td>" +
                        "<td>" + (oneFile.fileSize / 1048576).toFixed(2) + "MB" + "</td>" +
                        "<td><button type='button' class='btn btn-info' onclick=\"window.open('/File/downloadFile/" + oneFile.fileId + "')\">下载</button></td>" +
                        "<td><button type='button' class='btn btn-info' onclick=\"window.open('/File/fileDetail/" + oneFile.fileId + "')\">详情</button></td>" +
                        "<td><button type='button' class='btn btn-warning' onclick='shareFile(" + oneFile.fileId + ")'>分享</button></td>" +
                        "<td><button type='button' class='btn btn-danger' onclick='deleteConfirm(" + oneFile.fileId + ")'>删除</button></td>" +
                        "</tr>"
                    );
                }
            );
        },
    })
    $.ajax({
        type: "GET",
        url: "/File/getCurrentFile",
        success: function (data) {
            $("#currentPath").text(data.file_Path);
        },
    })
    $.ajax({
        type: "GET",
        url: "/User/getCurrentUser",
        success: function (data) {
            $("#releaseDateSize").text("剩余空间：" + ((data.dataMax - data.dataSize) / 1048576).toFixed(2) + " MB");
        },
    })

}

//登出
function logout() {
    $.ajax({
        url: 'logout',
        type: 'GET',
        success: function (data) {
            if (data == true) {
                toastr.success("登出成功！")
                setTimeout("indexLogin()", 1500);
                window.location.reload();
            } else {
                toastr.error("未知错误，登出失败！")
            }
        }
    })
}

//返回
function goBack() {
    $.ajax({
        url: '/File/goBack',
        type: 'GET',
        success: function (data) {
            if (data == true) {
                document.getElementById("goBackButton").disabled = false;
                refreshData();
            } else {
                document.getElementById("goBackButton").disabled = true;
                refreshData();
            }
        }
    })
}

//获取回收站
function getDeletedFile() {
    $.ajax({
            url: "/File/getDeletedFile",
            type: 'GET',
            success: function (data) {
                if (data == true) {
                    $("#fileList").empty("");
                    $.ajax({
                        type: "get",
                        url: "/File/refreshData",
                        success: function (data) {
                            $(data).each(
                                function (i, oneFile) {
                                    $("#fileList").append(
                                        "<tr>" +
                                        "<td>" + oneFile.fileName + "</td>" +
                                        "<td>" + oneFile.updateTime + "</td>" +
                                        "<td>" + oneFile.fileType + "</td>" +
                                        "<td>" + (oneFile.fileSize / 1048576).toFixed(2) + "MB" + "</td>" +
                                        "<td><button type='button' class='btn btn-info' onclick='restoreFile(" + oneFile.fileId + ")'>恢复</button></td>" +
                                        "<td><button type='button' class='btn btn-danger' onclick='removeFile(" + oneFile.fileId + ")'>彻底删除</button></td>" +
                                        "</tr>"
                                    );
                                }
                            );
                        },
                    })
                    $.ajax({
                        type: "GET",
                        url: "/File/getCurrentFile",
                        success: function (data) {
                            $("#currentPath").text(data.file_Path);
                        },
                    })
                    $.ajax({
                        type: "GET",
                        url: "/User/getCurrentUser",
                        success: function (data) {
                            $("#releaseDateSize").text("剩余空间：" + ((data.dataMax - data.dataSize) / 1048576).toFixed(2) + " MB");
                        },
                    })
                } else {
                    toastr.error("未知错误！");
                }
            }
        }
    )
}


//新建文件夹
function newFileFold() {
    const foldName = $('#newFileFoldName').val();
    $('#newFileFoldName').val("");
    if (foldName == "") {
        $('#newFileFoldName').attr('placeholder', "名称不能为空！");
    } else {
        $.ajax({
            url: '/File/newFileFold/' + foldName,
            type: 'GET',
            success: function (data) {
                if (data == true) {
                    $('#newFileFoldName').attr('placeholder', "请输入文件夹名称！");
                    $('#newFoldBox').modal('hide');
                    toastr.success("新建文件夹成功！");
                    refreshData();
                } else {
                    $('#newFileFoldName').attr('placeholder', "文件夹已存在！");
                }
            }
        })
    }
}

function chooseUpFile() {
    document.getElementById("uploadFileInput").click();
}

// 上传文件
function uploadFile() {
    const checkFile = $("#uploadFileInput").val();
    let dataRelease = 0;
    //获取剩余空间
    $.ajax({
        type: "GET",
        url: "/User/getCurrentUser",
        async: false,
        success: function (data) {
            dataRelease = data.dataMax - data.dataSize;
        },
    })
    if (null == checkFile || "" == checkFile) {
        toastr.warning("文件为空,请选择文件！");
        return;
    } else {
        if ($("#uploadFileInput")[0].files[0].size > dataRelease) {
            toastr.warning("剩余空间不足！");
            return;
        } else {
            const formData = new FormData(document.getElementById("uploadFileForm"));
            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: '/File/uploadFile',
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data == "请上传一个文件！") {
                        toastr.warning(data);
                        $('#uploadFileInput').val('');
                        return;
                    } else if (data == "当前文件已存在！") {
                        toastr.warning(data);
                        $('#uploadFileInput').val('');
                        return;
                    } else if (data == "上传成功！") {
                        toastr.success(data);
                        $('#uploadFileInput').val('');
                        refreshData();
                        return;
                    } else {
                        $('#uploadFileInput').val('');
                        toastr.error(data);
                        return;
                    }

                },
                xhr: function () {
                    var xhr = $.ajaxSettings.xhr();
                    if (xhr.upload) {
                        //处理进度条的事件
                        xhr.upload.addEventListener("progress", progressHandle, false);
                        //加载完成的事件
                        xhr.addEventListener("load", completeHandle, false);
                        //加载出错的事件
                        xhr.addEventListener("error", failedHandle, false);
                        //加载取消的事件
                        xhr.addEventListener("abort", canceledHandle, false);
                        //开始显示进度条
                        showProgress();
                        return xhr;
                    }
                }
            }, 'json');
        }
    }
}

var start = 0;

//显示进度条的函数
function showProgress() {
    start = new Date().getTime();
    $(".progress-body").css("display", "block");
}

//隐藏进度条的函数
function hideProgress() {
    $('.progress-body .progress-speed').html("0 M/S, 0/0M");
    $('.progress-body percentage').html("0%");
    $('.progress-body .progress-info').html("请选择文件并点击上传文件按钮");
    $(".progress-body").css("display", "none");
}

//进度条更新
function progressHandle(e) {
    $('.progress-body progress').attr({value: e.loaded, max: e.total});
    var percent = e.loaded / e.total * 100;
    var time = ((new Date().getTime() - start) / 1000).toFixed(3);
    if (time == 0) {
        time = 1;
    }
    $('.progress-body .progress-speed').html(((e.loaded / 1024) / 1024 / time).toFixed(2) + "M/S, " + ((e.loaded / 1024) / 1024).toFixed(2) + "/" + ((e.total / 1024) / 1024).toFixed(2) + " MB. ");
    $('.progress-body percentage').html(percent.toFixed(2) + "%");
    if (percent == 100) {
        $('.progress-body .progress-info').html("上传完成,正在处理");
    } else {
        $('.progress-body .progress-info').html("文件上传中...");
    }
}

//上传完成处理函数
function completeHandle(e) {
    $('.progress-body .progress-info').html("上传文件完成。");
    setTimeout(hideProgress, 2000);
}

//上传出错处理函数
function failedHandle(e) {
    $('.progress-body .progress-info').html("上传文件出错, 服务不可用或文件过大。");
}

//上传取消处理函数
function canceledHandle(e) {
    $('.progress-body .progress-info').html("上传文件取消。");
}


//访问目标
function clickFile(fileId, fileType) {
    if (fileType == '文件夹') {
        // 访问的是文件夹
        $.ajax({
            url: '/File/refreshFile/' + fileId,
            type: 'GET',
            success: function (data) {
                if (data == true) {
                    document.getElementById("goBackButton").disabled = false;
                    refreshData();
                } else {
                    alert("访问文件夹失败！")
                }
            }
        })
    } else {
        //访问的是文件


    }
}

//删除目标
function deleteConfirm(fileId) {
    $('#deleteFileId').val(fileId);
    $('#deleteConfirm').modal('show');
}

function deleteFile() {
    var fileId = $('#deleteFileId').val();
    $('#deleteConfirm').modal('hide');
    $.ajax({
        url: '/File/deleteFile/' + fileId,
        type: 'GET',
        success: function (data) {
            toastr.warning(data);
            $('#deleteFileId').val("");
            refreshData();
        }
    })
}

//永久删除文件
function removeFile(fileId) {
    $.ajax({
        url: '/File/removeFile/' + fileId,
        type: 'GET',
        success: function (data) {
            toastr.warning(data);
            getDeletedFile();
        }
    })
}

//恢复文件
function restoreFile(fileId) {
    $.ajax({
        url: '/File/restoreFile/' + fileId,
        type: 'GET',
        success: function (data) {
            toastr.warning(data);
            refreshData();
        }
    })
}


//返回首页
function goHome() {
    $.ajax({
        url: '/File/home',
        type: 'GET',
        success: function (data) {
            refreshData();
        }
    })
}


function shareFile(fileId) {
    $('#shareId').val(fileId);
    $('#shareFileBox').modal('show');
}

function shareFileSubmit() {
    var fileId = $('#shareId').val();
    var shareDay = $("input[name='shareFileDay']:checked").val();
    $('#shareFileBox').modal('hide');
    $.ajax({
        url: '/File/shareFile/' + fileId + '/' + shareDay,
        type: 'GET',
        success: function (data) {
            alert("分享码为：" + data);
            $('#shareId').val("");
        }
    })
}

function searchFile() {
    var fileName = $('#searchFileName').val();
    $("#fileList").empty("");
    $.ajax({
        url: '/File/searchFile/' + fileName,
        type: 'GET',
        success: function (data) {
            if (data == null) {
                toastr.warning("未找到目标文件！")
            } else {
                $(data).each(
                    function (i, oneFile) {
                        $("#fileList").append(
                            "<tr>" +
                            "<td><a onclick='clickFile(\"" + oneFile.fileId + "\",\"" + oneFile.fileType + "\")'>" + oneFile.fileName + "</a></td>" +
                            "<td>" + oneFile.updateTime + "</td>" +
                            "<td>" + oneFile.fileType + "</td>" +
                            "<td>" + (oneFile.fileSize / 1048576).toFixed(2) + "MB" + "</td>" +
                            "<td><button type='button' class='btn btn-info' onclick=\"window.open('/File/downloadFile/" + oneFile.fileId + "')\">下载</button></td>" +
                            "<td><button type='button' class='btn btn-info' onclick=\"window.open('/File/fileDetail/" + oneFile.fileId + "')\">详情</button></td>" +
                            "<td><button type='button' class='btn btn-warning' onclick='shareFile(" + oneFile.fileId + ")'>分享</button></td>" +
                            "<td><button type='button' class='btn btn-danger' onclick='deleteConfirm(" + oneFile.fileId + ")'>删除</button></td>" +
                            "</tr>"
                        );
                    }
                );
            }
        }
    })
}