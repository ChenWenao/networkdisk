package cwa.dao;

import cwa.bean.NetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate usertemplate;
    private UserRowMapper userRowMapper = new UserRowMapper();

    //增
    public boolean insertNewUser(NetUser user) {
        try {
            usertemplate.update("insert into user(username,password,email,phone) values(?,?,?,?)",
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPhone());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    //删


    //改
public boolean updateUserSize(int userId,String operator,long dataSize){
    try {
        if ("+".equals(operator))
            usertemplate.update("update networkdisk.user set dataSize =dataSize+ ? where userId=?",  dataSize, userId);
        if ("-".equals(operator))
            usertemplate.update("update networkdisk.user set dataSize =dataSize- ? where userId=?",  dataSize, userId);
        return true;
    } catch (Exception e) {
        System.out.println(e);
        return false;
    }
}

    //查
    public NetUser selectOneUser(NetUser user) {
        try {
            List<NetUser> userSelectResult = usertemplate.query("select * from user where username=? or email=? or phone=?", userRowMapper, user.getUsername(), user.getEmail(), user.getPhone());
            return userSelectResult.get(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public NetUser selectUserByUserId(int userId){
        try {
            List<NetUser> userSelectResult = usertemplate.query("select * from user where userId=?", userRowMapper, userId);
            return userSelectResult.get(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
