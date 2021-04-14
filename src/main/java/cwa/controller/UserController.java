package cwa.controller;

import cwa.bean.NetFile;
import cwa.bean.NetUser;
import cwa.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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

    //返回个人主页
    @GetMapping("/User/homepage")
    public ModelAndView homepage(HttpSession session) {
        ModelAndView mav = new ModelAndView("homepage.html");
        mav.addObject("currentUser", session.getAttribute("currentUser"));
        return mav;
    }

    //登出
    @GetMapping("/User/logout")
    public boolean logOut(HttpSession session) {
        try {
            session.removeAttribute("currentUser");
            session.removeAttribute("currentFile");
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //注册功能
    @PostMapping("/User/register")
    public boolean register(NetUser regUser) throws NoSuchAlgorithmException {
        return userService.addNewUser(regUser);
    }

    //登录功能
    @PostMapping("/User/login")
    public boolean login(NetUser logUser, HttpSession session) throws NoSuchAlgorithmException {
        NetUser currentUser = userService.loginCheck(logUser);
        if (currentUser != null) {
            //设置登录用户
            session.setAttribute("currentUser", currentUser);
            //设置根目录
            NetFile currentFile = new NetFile();
            currentFile.setFile_userId(currentUser.getUserId());
            currentFile.setFileId(0);
            currentFile.setFile_Path("/");
            currentFile.setFileStatus(0);

            session.setAttribute("currentFile", currentFile);

            return true;
        } else
            return false;
    }

    //获取当前用户
    @GetMapping("/User/getCurrentUser")
    public NetUser getCurrentUser(HttpSession session) {

        return (NetUser) session.getAttribute("currentUser");
    }


}
