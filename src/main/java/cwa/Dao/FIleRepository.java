package cwa.Dao;

import cwa.Bean.file;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileRepository {
    @Autowired
    private JdbcTemplate filetemplate;
    private FileRowMapper fileRowMapper = new FileRowMapper();

    //增
    // 文件夹
    public boolean insertNewFileFold(file motherFile, int userId, String newFoldName) {
        try {
            filetemplate.update("insert into networkdisk.file(file_userId,file_parentId,file_path, filename) values (?,?,?,?)",
                    userId,
                    motherFile.getFileId(),
                    motherFile.getFile_Path() + "/"+newFoldName,
                    newFoldName);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    //查
    //文件夹
    public file selectFile(file motherFile, int userId, String fileName,String fileType) {
        try {
            List<file> files = filetemplate.query("select * from networkdisk.file where file_parentId=? and file_userId=? and filename=? and filetype=?",
                    fileRowMapper,
                    motherFile.getFileId(),
                    userId,
                    fileName,
                    fileType);
            return files.get(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    //查某人(userId)的某个文件夹(fileId)下的文件列表
    public List<file> selectUserFilesByParentId(int fileId, int userId) {
        try {
            List<file> files = filetemplate.query("select * from file where file_parentId=? and file_userId=?", fileRowMapper, fileId, userId);
            return files;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    //查某人(userId)的某个文件(fileId)信息
    public file selectUserFileById(int fileId, int userId) {
        try {
            List<file> files = filetemplate.query("select * from file where fileId=? and file_userId=?", fileRowMapper, fileId, userId);
            return files.get(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
