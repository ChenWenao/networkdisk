package cwa.Service;

import cwa.Dao.FIleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private FIleRepository fileRepository;

}
