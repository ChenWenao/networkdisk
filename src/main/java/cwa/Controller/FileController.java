package cwa.Controller;

import cwa.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/File/newFileFold/{foldName}")
    public boolean newFileFold(@PathVariable("foldName")String newFileFoldName){
        try {
            System.out.println(newFileFoldName);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
