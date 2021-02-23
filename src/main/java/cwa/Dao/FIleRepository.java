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
                    motherFile.getFile_Path() + newFoldName,
                    newFoldName);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    //查
    //文件夹
    public file selectFile(file motherFile, int userId, file targetFile) {
        try {
            List<file> files = filetemplate.query("select * from networkdisk.file where file_parentId=? and file_userId=? and filename=? and filetype=?",
                    fileRowMapper,
                    motherFile.getFileId(),
                    userId,
                    targetFile.getFileName(),
                    targetFile.getFileType());
            return files.get(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
