package cwa.controller;

import cwa.bean.NetFile;
import cwa.bean.NetUser;
import cwa.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    //增
    //新建文件夹
    @GetMapping("/File/newFileFold/{foldName}")
    public boolean newFileFold(@PathVariable("foldName") String newFileFoldName, HttpSession session) {
        try {
            return fileService.addNewFileFold((NetFile) session.getAttribute("currentFile"), ((NetUser) session.getAttribute("currentUser")).getUserId(), newFileFoldName);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //上传文件
    @PostMapping("/File/uploadFile")
    public String newFile(@RequestParam("upFile") MultipartFile upFile, HttpSession session) throws IOException {
        NetUser currentUser = (NetUser) session.getAttribute("currentUser");
        //判断是否传入图片。
        if (upFile.isEmpty()) {
            //无文件传入
            return "请上传一个文件！";
        } else {
            //有文件传入
            //新文件需要字段：filename,filetype,filesize,filelocation,file_userId,file_parentId,file_path
            NetFile newFile = new NetFile();

            //设置fileName。
            String originalFileName = upFile.getOriginalFilename();
            newFile.setFileName(originalFileName.substring(0, originalFileName.lastIndexOf(".")));
            newFile.setFileType(originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length()));
            newFile.setFileSize(upFile.getSize());
            newFile.setFile_userId(((NetUser) session.getAttribute("currentUser")).getUserId());
            newFile.setFile_parentId(((NetFile) session.getAttribute("currentFile")).getFileId());
            if (fileService.getUserFileByOthers(newFile) != null) {
                //有重名文件
                return "当前文件已存在！";
            } else {
                newFile.setFile_Path(((NetFile) session.getAttribute("currentFile")).getFile_Path() + "/" + newFile.getFileName());
                //获取课程图片存储文件夹，若不存在，就创建文件夹。
                String fileDirPath = "src/main/resources/static/data/userdata/" + ((NetUser) session.getAttribute("currentUser")).getUsername() + ((NetFile) session.getAttribute("currentFile")).getFile_Path() + "/" + originalFileName;
                File fileDir = new File(fileDirPath);
                if (!fileDir.exists()) {
                    // 递归生成文件夹
                    fileDir.mkdirs();
                }
                try {
                    // 构建真实的文件路径
                    File realFile = new File(fileDir.getAbsolutePath());
                    // 上传图片到绝对路径
                    upFile.transferTo(realFile);
                    //System.out.println("上传成功！");
                    //设置课程图片Logo。
                    newFile.setFileLocation(realFile.getAbsolutePath());
                    fileService.addNewFile(newFile, ((NetUser) session.getAttribute("currentUser")).getUserId());
                    currentUser.setDataSize(currentUser.getDataSize() + newFile.getFileSize());
                    return "上传成功！";
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        return "未知错误！";
    }


    //删
    @GetMapping("/File/removeFile/{fileId}")
    public String removeFile(@PathVariable(value = "fileId") int fileId, HttpSession session) {
        try {
            NetUser currentUser = (NetUser) session.getAttribute("currentUser");
            NetFile reFile = fileService.getUserFileById(fileId);
            currentUser.setDataSize(currentUser.getDataSize() - reFile.getFileSize());

            if (fileService.removeFile(reFile, currentUser.getUserId()))
                return "彻底删除成功！";
            else
                return "彻底删除失败！";
        } catch (Exception e) {
            System.out.println(e);
            return "彻底删除失败！";
        }
    }


    //改
    @GetMapping("/File/deleteFile/{fileId}")
    public String deleteFile(@PathVariable(value = "fileId") int fileId) {
        if (fileService.changeFileStatus(fileId, 1)) {
            return "删除文件成功！";
        } else {
            return "删除失败！";
        }
    }



    @GetMapping("/File/restoreFile/{fileId}")
    public String restoreFile(@PathVariable(value = "fileId") int fileId) {
        if (fileService.changeFileStatus(fileId, 0)) {
            return "恢复文件成功！";
        } else {
            return "恢复失败！";
        }
    }

    @GetMapping("/File/home")
    public boolean goHome(HttpSession session) {
        NetUser currentUser = (NetUser) session.getAttribute("currentUser");
        //设置根目录
        NetFile currentFile = new NetFile();
        currentFile.setFile_userId(currentUser.getUserId());
        currentFile.setFileId(0);
        currentFile.setFile_Path("/");
        currentFile.setFileStatus(0);
        session.setAttribute("currentFile", currentFile);
        return true;

    }

    @GetMapping("/File/getDeletedFile")
    public boolean getDeleteFile(HttpSession session) {
        try {
            NetFile file = (NetFile) session.getAttribute("currentFile");
            file.setFileStatus(1);
            session.setAttribute("currentFile", file);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //查
    //刷新数据
    @GetMapping("/File/refreshData")
    public List<NetFile> refreshData(HttpSession session) {
        return fileService.getUserFilesByParentId(((NetFile) session.getAttribute("currentFile")).getFileId(), ((NetUser) session.getAttribute("currentUser")).getUserId(), ((NetFile) session.getAttribute("currentFile")).getFileStatus());
    }

    //设置新的currentFile
    @GetMapping("/File/refreshFile/{fileId}")
    public boolean refreshFile(@PathVariable("fileId") int fileId, HttpSession session) {
        try {
            session.setAttribute("currentFile", fileService.getUserFileById(fileId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //返回上一页
    @GetMapping("/File/goBack")
    public boolean goBack(HttpSession session) {
        NetFile currentFile = ((NetFile) session.getAttribute("currentFile"));


        if (currentFile.getFile_parentId() == 0) {
            //设置根目录
            currentFile.setFile_userId(currentFile.getUserId());
            currentFile.setFileId(0);
            currentFile.setFile_Path("/");
            session.setAttribute("currentFile", currentFile);
            return false;
        } else {
            NetFile newCurrentFile = fileService.getUserFileById(currentFile.getFile_parentId());
            session.setAttribute("currentFile", newCurrentFile);
            return true;
        }
    }

    //获取当前根目录
    @GetMapping("/File/getCurrentFile")
    public NetFile getCurrentFile(HttpSession session) {
        return (NetFile) session.getAttribute("currentFile");
    }


    @GetMapping("/File/searchFile/{fileName}")
        public List<NetFile> searchFile(@PathVariable(value = "fileName")String seFileName, HttpSession session){
        return fileService.searchUserFileByFileName(((NetUser) session.getAttribute("currentUser")).getUserId(),seFileName);
    }
    //下载文件
    @GetMapping("/File/downloadFile/{downloadId}")
    public String downloadFile(@PathVariable("downloadId") int downId, HttpServletResponse response, HttpSession session) {
        NetFile downFile = fileService.getUserFileById(downId);
        if (downFile.getFile_userId() != ((NetUser) session.getAttribute("currentUser")).getUserId())
            return null;
        else {
            String fileName = downFile.getFileName() + "." + downFile.getFileType();
            //设置文件路径
            String realPath = downFile.getFileLocation().substring(0, downFile.getFileLocation().lastIndexOf('\\'));
            File file = new File(realPath + '/' + fileName);
            if (!file.exists()) {
                return "下载文件不存在";
            }
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
                byte[] buff = new byte[1024];
                OutputStream os = response.getOutputStream();
                int i = 0;
                while ((i = bis.read(buff)) != -1) {
                    os.write(buff, 0, i);
                    os.flush();
                }
            } catch (IOException e) {
                return "下载失败";
            }
            return "下载成功";
        }
    }

    @GetMapping("/File/fileDetail/{fileId}")
    public ModelAndView fileDetail(@PathVariable(value = "fileId")int fileId){
        ModelAndView mav=new ModelAndView();
        NetFile netFile=fileService.getUserFileById(fileId);
        mav.setViewName("filedetail");
        mav.addObject("netFile",netFile);
        return mav;
    }

}

