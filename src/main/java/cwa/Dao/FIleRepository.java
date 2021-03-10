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
    public boolean insertNewFileFold(file newFold) {
        try {
            filetemplate.update("insert into networkdisk.file(file_userId,file_parentId,file_path, filename) values (?,?,?,?)",
                    newFold.getFile_userId(),
                    newFold.getFile_parentId(),
                    newFold.getFile_Path(),
                    newFold.getFileName());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //文件
    public boolean insertNewFile(file newFile) {
        try {
            //新文件需要字段：filename,filetype,filesize,filelocation,file_userId,file_parentId,file_path
            filetemplate.update("insert into networkdisk.file(filename,filetype,filesize,filelocation,file_userId,file_parentId,file_path) values (?,?,?,?,?,?,?)",
                    newFile.getFileName(),
                    newFile.getFileType(),
                    newFile.getFileSize(),
                    newFile.getFileLocation(),
                    newFile.getFile_userId(),
                    newFile.getFile_parentId(),
                    newFile.getFile_Path());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    //查
    //文件夹
    //根据其他信息查某人的某个文件
    public file selectFile(file targetFile) {
        try {
            List<file> files = filetemplate.query("select * from networkdisk.file where file_parentId=? and file_userId=? and filename=? and filetype=?",
                    fileRowMapper,
                    targetFile.getFile_parentId(),
                    targetFile.getFile_userId(),
                    targetFile.getFileName(),
                    targetFile.getFileType());
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

    //根据id查某人(userId)的某个文件
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
