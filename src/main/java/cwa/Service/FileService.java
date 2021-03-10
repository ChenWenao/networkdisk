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
        newFold.setFile_parentId(motherFile.getFileId());
        newFold.setFile_userId(userId);
        newFold.setFileName(sonFileName);
        newFold.setFileType("文件夹");

        if (fileRepository.selectFile(newFold) == null) {
            newFold.setFile_Path(motherFile.getFile_Path()+"/"+sonFileName);
            return fileRepository.insertNewFileFold(newFold);
        } else {
            return false;
        }
    }

    public boolean addNewFile(file newFile){
        return fileRepository.insertNewFile(newFile);
    }

    //根据其他信息查某人的某个文件
    public file getUserFileByOthers(file targetFile){
        return fileRepository.selectFile(targetFile);
    }

    //查找文件夹下的内容
    public List<file> getUserFilesByParentId(int fileId, int userId){
        return fileRepository.selectUserFilesByParentId(fileId, userId);
    }

    //根据id查某人的某个文件
    public file getUserFileById(int fileId, int userId){
        return fileRepository.selectUserFileById(fileId, userId);
    }


}
