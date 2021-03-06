package cwa.dao;

import cwa.bean.NetFile;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class FileRowMapper implements RowMapper<NetFile> {
    @Override
    public NetFile mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        NetFile file = new NetFile();
        file.setFileId(resultSet.getInt("fileId"));
        file.setFileName(resultSet.getString("filename"));
        file.setUploadTime(resultSet.getDate("uploadtime"));
        file.setUpdateTime(resultSet.getDate("updatetime"));
        file.setFileType(resultSet.getString("filetype"));
        file.setFileSize(resultSet.getLong("filesize"));
        file.setFileStatus(resultSet.getInt("filestatus"));
        file.setFileLocation(resultSet.getString("fileLocation"));

        file.setFile_userId(resultSet.getInt("file_userId"));
        file.setFile_parentId(resultSet.getInt("file_parentId"));
        file.setFile_Path(resultSet.getString("file_path"));

        return file;
    }
}
