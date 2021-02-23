package cwa.Service;

import cwa.Bean.file;
import cwa.Dao.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public boolean addNewFileFold(file motherFile, int userId, String sonFileName) {
        file newFold = new file();
        newFold.setFileName(sonFileName);
        newFold.setFileType("fold");
        if (fileRepository.selectFile(motherFile, userId, newFold.getFileName(),newFold.getFileType()) == null) {
            return fileRepository.insertNewFileFold(motherFile, userId, sonFileName);
        } else {
            return false;
        }
    }

    public List<file> getUserFilesByParentId(int fileId, int userId){
        return fileRepository.selectUserFilesByParentId(fileId, userId);
    }

    public file getUserFileById(int fileId, int userId){
        return fileRepository.selectUserFileById(fileId, userId);
    }


}
