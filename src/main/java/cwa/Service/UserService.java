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
    public boolean addNewUser(user user) throws NoSuchAlgorithmException {
        user userSelect = userRepository.selectOneUser(user);
        if (userSelect != null)
            return false;
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
    public user loginCheck(user user) throws NoSuchAlgorithmException {
        //加密密码
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(user.getPassword().getBytes());
        String password_MD5 = new BigInteger(1, md5.digest()).toString(16);
        user.setPassword(password_MD5);
        //查找用户
        user userSelect = userRepository.selectOneUser(user);

        if (userSelect == null || !userSelect.getPassword().equals(user.getPassword())) {
            return null;
        } else {
            return userSelect;
        }
    }
}

