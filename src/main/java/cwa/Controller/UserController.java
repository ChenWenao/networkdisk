package cwa.Controller;

import cwa.Bean.user;
import cwa.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/User/register")
    public String register(@ModelAttribute(value = "regUser")user regUser){
        System.out.println(regUser.getUserName());
        System.out.println(regUser.getPassword());
        return "成功";
    }




}
