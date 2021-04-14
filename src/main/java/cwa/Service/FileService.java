package cwa.service;

import cwa.bean.NetFile;
import cwa.dao.FileRepository;
import cwa.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;


    //增
    public boolean addNewFileFold(NetFile motherFile, int userId, String sonFileName) {
        NetFile newFold = new NetFile();
        newFold.setFile_parentId(motherFile.getFileId());
        newFold.setFile_userId(userId);
        newFold.setFileName(sonFileName);
        newFold.setFileType("文件夹");

        if (fileRepository.selectFile(newFold) == null) {
            newFold.setFile_Path(motherFile.getFile_Path() + "/" + sonFileName);
            return fileRepository.insertNewFileFold(newFold);
        } else {
            return false;
        }
    }

    public boolean addNewFile(NetFile newFile, int userId) {
        try {
            String filePath = newFile.getFile_Path();
            do {
                filePath = filePath.substring(0, filePath.lastIndexOf('/'));
                fileRepository.updateFoldSize(filePath, "+", newFile.getFileSize());
                System.out.println(filePath);
            } while (!"/".equals(filePath));
            return fileRepository.insertNewFile(newFile) && userRepository.updateUserSize(userId, "+", newFile.getFileSize());
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //删
    public boolean removeFile(NetFile reFile, int userId) {
        try {
            return fileRepository.deleteFile(reFile, userId) && deleteDir(new File(reFile.getFileLocation())) && userRepository.updateUserSize(userId, "-", reFile.getFileSize());
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //改
    public boolean changeFileStatus(int fileId, int fileStatus) {
        return fileRepository.setFileStatus(fileId, fileStatus);
    }


    //查
    //根据其他信息查某人的某个文件
    public NetFile getUserFileByOthers(NetFile targetFile) {
        return fileRepository.selectFile(targetFile);
    }

    //查找文件夹下的内容
    public List<NetFile> getUserFilesByParentId(int fileId, int userId, int fileStatus) {
        return fileRepository.selectUserFilesByParentId(fileId, userId, fileStatus);
    }

    //根据id查某人的某个文件
    public NetFile getUserFileById(int fileId) {
        return fileRepository.selectUserFileById(fileId);
    }


    //删除文件
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
