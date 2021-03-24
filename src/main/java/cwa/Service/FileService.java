package cwa.service;

import cwa.bean.NetFile;
import cwa.dao.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public boolean addNewFileFold(NetFile motherFile, int userId, String sonFileName) {
        NetFile newFold = new NetFile();
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

    public boolean addNewFile(NetFile newFile){
        return fileRepository.insertNewFile(newFile);
    }

    //根据其他信息查某人的某个文件
    public NetFile getUserFileByOthers(NetFile targetFile){
        return fileRepository.selectFile(targetFile);
    }

    //查找文件夹下的内容
    public List<NetFile> getUserFilesByParentId(int fileId, int userId){
        return fileRepository.selectUserFilesByParentId(fileId, userId);
    }

    //根据id查某人的某个文件
    public NetFile getUserFileById(int fileId, int userId){
        return fileRepository.selectUserFileById(fileId, userId);
    }


}
