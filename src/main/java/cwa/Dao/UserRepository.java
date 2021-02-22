package cwa.Dao;

import cwa.Bean.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate usertemplate;
    private UserRowMapper userRowMapper=new UserRowMapper();

//增
    public boolean insertNewUser(user user){
        try {
            usertemplate.update("insert into user(username,password,email,phone) values(?,?,?,?)",
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPhone());
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

    }
    //删



    //改


    //查
    public user selectOneUser(user user){
        try {
        List<user> userSelectResult=usertemplate.query("select * from user where username=? or email=? or phone=?",userRowMapper,user.getUsername(),user.getEmail(),user.getPhone());
        return userSelectResult.get(0);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


}
