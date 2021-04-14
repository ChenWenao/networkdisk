package cwa.service;

import cwa.bean.NetUser;
import cwa.dao.UserRepository;
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
    public boolean addNewUser(NetUser user) throws NoSuchAlgorithmException {
        NetUser userSelect = userRepository.selectOneUser(user);
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
    public NetUser loginCheck(NetUser user) throws NoSuchAlgorithmException {
        //加密密码
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(user.getPassword().getBytes());
        String password_MD5 = new BigInteger(1, md5.digest()).toString(16);
        user.setPassword(password_MD5);
        //查找用户
        NetUser userSelect = userRepository.selectOneUser(user);

        if (userSelect == null || !userSelect.getPassword().equals(user.getPassword())) {
            return null;
        } else {
            return userSelect;
        }
    }

}

