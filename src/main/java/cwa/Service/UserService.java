package cwa.Service;

import cwa.Bean.user;
import cwa.Dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //增
    public String addNewUser(user user) throws NoSuchAlgorithmException {
        user userSelect = userRepository.selectOneUser(user);
        if (userSelect != null)
            return "当前用户已注册，请登录！";
        else {
            //加密密码
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(user.getPassword().getBytes());
            String password_MD5 = new BigInteger(1, md5.digest()).toString(16);
            user.setPassword(password_MD5);
            return userRepository.insertNewUser(user);
        }
    }


    //删

//改

//查
    public String loginCheck(user user) throws NoSuchAlgorithmException {
        //加密密码
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(user.getPassword().getBytes());
        String password_MD5 = new BigInteger(1, md5.digest()).toString(16);
        user.setPassword(password_MD5);
        //查找用户
        user userSelect = userRepository.selectOneUser(user);

        if (userSelect == null)
            return "用户名不存在，请注册！";
        else if (!userSelect.getPassword().equals(user.getPassword()))
            return "用户名或密码错误，请重新输入！";
        else
            return "登录成功！";
    }
}

