package cwa.dao;

import cwa.bean.NetFile;
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
    public boolean insertNewFileFold(NetFile newFold) {
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
    public boolean insertNewFile(NetFile newFile) {
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

    //删
    public boolean deleteFile(NetFile reFile,int userId){
        try {
            filetemplate.update("update networkdisk.file set fileSize=fileSize-? where file_userId=? and INSTR(?,file_path)>0",reFile.getFileSize(),userId,reFile.getFile_Path());
            filetemplate.update("delete from networkdisk.file where file_userId=? and file_path like ?",userId,reFile.getFile_Path()+"%");
            filetemplate.update("delete from networkdisk.file where fileId=?",reFile.getFileId());
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    //改
    public boolean updateFoldSize(String upFilePath, String operator, long fileSize) {
        try {
            if ("+".equals(operator))
                filetemplate.update("update networkdisk.file set fileSize =fileSize+ ? where file_path=?",  fileSize, upFilePath);
            if ("-".equals(operator))
                filetemplate.update("update networkdisk.file set fileSize =fileSize- ? where file_path=?",  fileSize, upFilePath);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean setFileStatus(int fileId,int fileStauts){
        try {
            filetemplate.update("update networkdisk.file set filestatus = ? where fileId=?",fileStauts,fileId);
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }


    //查
    //文件夹
    //根据其他信息查某人的某个文件
    public NetFile selectFile(NetFile targetFile) {
        try {
            List<NetFile> files = filetemplate.query("select * from networkdisk.file where file_parentId=? and file_userId=? and filename=? and filetype=? and filestatus=0",
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
    public List<NetFile> selectUserFilesByParentId(int fileId, int userId,int fileStatus) {
        try {
            List<NetFile> files=null;
            if(fileStatus==0) {
                files = filetemplate.query("select * from file where file_parentId=? and file_userId=? and filestatus=0", fileRowMapper, fileId, userId);
            }else if(fileStatus==1){
                files = filetemplate.query("select * from file where file_userId=? and filestatus=1", fileRowMapper, userId);
            }
            return files;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    //根据id查某个文件
    public NetFile selectUserFileById(int fileId) {
        try {
            List<NetFile> files = filetemplate.query("select * from file where fileId=? ", fileRowMapper, fileId);
            return files.get(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
