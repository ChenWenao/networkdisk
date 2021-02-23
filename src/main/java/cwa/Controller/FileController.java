package cwa.Controller;

import cwa.Bean.file;
import cwa.Bean.user;
import cwa.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/File/newFileFold/{foldName}")
    public boolean newFileFold(@PathVariable("foldName") String newFileFoldName, HttpSession session) {
        try {
            return fileService.addNewFileFold((file) session.getAttribute("currentFile"), ((user) session.getAttribute("currentUser")).getUserId(), newFileFoldName);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
