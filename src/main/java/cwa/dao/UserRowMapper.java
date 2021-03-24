package cwa.dao;

import cwa.bean.NetUser;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<NetUser> {
    @Override
    public NetUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        NetUser user = new NetUser();
        user.setUserId(resultSet.getInt("userId"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));
        user.setCreateTime(resultSet.getDate("createtime"));
        user.setDataSize(resultSet.getDouble("datasize"));
        user.setDataMax(resultSet.getDouble("datamax"));
        return user;
    }
}
