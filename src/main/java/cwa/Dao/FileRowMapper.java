package cwa.Dao;

import cwa.Bean.file;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FileRowMapper implements RowMapper<file> {

    @Override
    public file mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        file file = new file();
        file.setId(resultSet.getInt("id"));
        file.setUserId(resultSet.getInt("userid"));
        file.setFileName(resultSet.getString("filename"));
        file.setUploadTime(resultSet.getDate("uploadtime"));
        file.setUpdateTime(resultSet.getDate("updatetime"));
        file.setParentPath(resultSet.getString("parentPath"));
        file.setParent(resultSet.getString("parent"));
        file.setFileType(resultSet.getString("filetype"));
        file.setFileSize(resultSet.getInt("filesize"));
        file.setFileStatus(resultSet.getInt("filestatus"));
        file.setFilePath(resultSet.getString("filepath"));
        return file;
    }
}
