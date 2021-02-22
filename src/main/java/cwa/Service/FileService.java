package cwa.Service;

import cwa.Bean.file;
import cwa.Dao.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public boolean addNewFileFold(file motherFile, int userId, String sonFileName) {
        file newFold = new file();
        newFold.setFileName(sonFileName);
        newFold.setFileType("fold");
        if (fileRepository.selectFile(motherFile, userId, newFold) == null) {
            return fileRepository.insertNewFileFold(motherFile, userId, sonFileName);
        } else {
            return false;
        }
    }

}
