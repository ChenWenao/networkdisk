package cwa.Controller;

import cwa.Bean.file;
import cwa.Bean.user;
import cwa.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    //新建文件夹
    @GetMapping("/File/newFileFold/{foldName}")
    public boolean newFileFold(@PathVariable("foldName") String newFileFoldName, HttpSession session) {
        try {
            return fileService.addNewFileFold((file) session.getAttribute("currentFile"), ((user) session.getAttribute("currentUser")).getUserId(), newFileFoldName);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //上传文件
    @PostMapping("/File/uploadFile")
    public String newFile(@RequestParam("upFile") MultipartFile upFile, HttpSession session) throws IOException {
        // https://www.icode9.com/content-4-49161.html
        //判断是否传入图片。
        if (upFile.isEmpty()) {
            //无文件传入
            return "请上传一个文件！";
        } else {
            //有文件传入
            //新文件需要字段：filename,filetype,filesize,filelocation,file_userId,file_parentId,file_path
            file newFile = new file();

            //设置fileName。
            String originalFileName = upFile.getOriginalFilename();
            newFile.setFileName(originalFileName.substring(0, originalFileName.lastIndexOf(".")));
            newFile.setFileType(originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length()));
            newFile.setFileSize(upFile.getSize());
            newFile.setFile_userId(((user) session.getAttribute("currentUser")).getUserId());
            newFile.setFile_parentId(((file) session.getAttribute("currentFile")).getFileId());
            if (fileService.getUserFileByOthers(newFile) != null) {
                //有重名文件
                return "当前文件已存在！";
            } else {
                newFile.setFile_Path(((file) session.getAttribute("currentFile")).getFile_Path() + "/"+newFile.getFileName());
                //获取课程图片存储文件夹，若不存在，就创建文件夹。
                String fileDirPath = "src/main/resources/static/data/userdata/" +((user) session.getAttribute("currentUser")).getUsername()+((file)session.getAttribute("currentFile")).getFile_Path()+"/"+originalFileName;
                File fileDir = new File(fileDirPath);
                if (!fileDir.exists()) {
                    // 递归生成文件夹
                    fileDir.mkdirs();
                }
                try {
                    // 构建真实的文件路径
                    File realFile = new File(fileDir.getAbsolutePath());
                    //输出文件路径。
                    // 上传图片到 -》 “绝对路径”
                    upFile.transferTo(realFile);
                    //System.out.println("上传成功！");
                    //设置课程图片Logo。
                    newFile.setFileLocation(realFile.getAbsolutePath());
                    fileService.addNewFile(newFile);
                    return "上传成功！";
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        return "未知错误！";
    }

    //刷新数据
    @GetMapping("/File/refreshData")
    public List<file> refreshData(HttpSession session) {
        return fileService.getUserFilesByParentId(((file) session.getAttribute("currentFile")).getFileId(), ((user) session.getAttribute("currentUser")).getUserId());
    }

    //设置新的currentFile
    @GetMapping("/File/refreshFile/{fileId}")
    public boolean refreshFile(@PathVariable("fileId") int fileId, HttpSession session) {
        try {
            session.setAttribute("currentFile", fileService.getUserFileById(fileId, ((user) session.getAttribute("currentUser")).getUserId()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //返回上一页
    @GetMapping("/File/goBack")
    public boolean goBack(HttpSession session) {
        file currentFile = ((file) session.getAttribute("currentFile"));


        if (currentFile.getFile_parentId() == 0) {
            //设置根目录
            currentFile.setFile_userId(currentFile.getUserId());
            currentFile.setFileId(0);
            currentFile.setFile_Path("/");
            session.setAttribute("currentFile", currentFile);
            return false;
        } else {
            file newCurrentFile = fileService.getUserFileById(currentFile.getFile_parentId(), ((user) session.getAttribute("currentUser")).getUserId());
            session.setAttribute("currentFile", newCurrentFile);
            return true;
        }
    }

    //获取当前根目录
    @GetMapping("/File/getCurrentFile")
    public file getCurrentFile(HttpSession session) {
        return (file) session.getAttribute("currentFile");
    }

}
