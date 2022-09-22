package com.xx.mail.controller;

import com.xx.mail.pojo.User;
import com.xx.mail.pojo.UserVo;
import com.xx.mail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * <h2> UserController </h2>
 *
 * @Description: TODO
 * @Author xiaoxing
 * @Date 2022/9/22
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession httpSession){
        userService.sendMimeMail(email, httpSession);
        return "sucess";
    }

    @PostMapping("/regist")
    @ResponseBody
    public String regist(UserVo userVo, HttpSession session){
        userService.registered(userVo,session);
        return "sucess";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(String email, String password){
        userService.loginIn(email,password);
        return "sucess";
    }


}
