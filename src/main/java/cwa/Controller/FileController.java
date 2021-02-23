package cwa.Controller;

import cwa.Bean.file;
import cwa.Bean.user;
import cwa.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
