package cwa.dao;

import cwa.bean.NetShare;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareRowMapper implements RowMapper<NetShare> {
    @Override
    public NetShare mapRow(ResultSet resultSet,int rowNum) throws SQLException {
        NetShare netShare=new NetShare();
        netShare.setUserId(resultSet.getInt("userId"));
        netShare.setUserName(resultSet.getString("username"));
        netShare.setPassword(resultSet.getString("password"));
        netShare.setEmail(resultSet.getString("email"));
        netShare.setPhone(resultSet.getString("phone"));
        netShare.setCreateTime(resultSet.getDate("createtime"));
        netShare.setDataSize(resultSet.getDouble("datasize"));
        netShare.setDataMax(resultSet.getDouble("datamax"));

        netShare.setFileId(resultSet.getInt("fileId"));
        netShare.setFileName(resultSet.getString("filename"));
        netShare.setUploadTime(resultSet.getDate("uploadtime"));
        netShare.setUpdateTime(resultSet.getDate("updatetime"));
        netShare.setFileType(resultSet.getString("filetype"));
        netShare.setFileSize(resultSet.getLong("filesize"));
        netShare.setFileStatus(resultSet.getInt("filestatus"));
        netShare.setFileLocation(resultSet.getString("fileLocation"));

        netShare.setFile_userId(resultSet.getInt("file_userId"));
        netShare.setFile_parentId(resultSet.getInt("file_parentId"));
        netShare.setFile_Path(resultSet.getString("file_path"));

        netShare.setShare_fileId(resultSet.getInt("share_fileId"));
        netShare.setShare_userId(resultSet.getInt("share_userId"));
        netShare.setShareTime(resultSet.getDate("sharetime"));
        netShare.setShareCode(resultSet.getString("shareCode"));
        netShare.setShareLifeTime(resultSet.getInt("shareLifeTime"));
        return netShare;
    }
}
