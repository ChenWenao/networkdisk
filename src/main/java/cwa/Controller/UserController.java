package cwa.Controller;

import cwa.Bean.user;
import cwa.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //返回登陆页面
    @GetMapping("/User/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("login.html");
        return mav;
    }

    //注册功能
    @PostMapping("/User/register")
    public String register(user regUser) throws NoSuchAlgorithmException {
        return userService.addNewUser(regUser);
    }

    //登录功能
    @PostMapping("/User/login")
    public String login(user logUser) throws NoSuchAlgorithmException {
        return userService.loginCheck(logUser);
    }
}
