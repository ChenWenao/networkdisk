package cwa.Dao;

import cwa.Bean.user;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<user> {
    @Override
    public user mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        user user = new user();
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
