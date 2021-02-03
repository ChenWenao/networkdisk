package cwa.Controller;

import cwa.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;

}
