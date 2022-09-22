package com.xx.mail.service;

import com.xx.mail.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.mail.pojo.UserVo;

import javax.servlet.http.HttpSession;

/**
* @author xx
* @description 针对表【user】的数据库操作Service
* @createDate 2022-09-22 15:09:54
*/
public interface UserService extends IService<User> {

    boolean sendMimeMail( String email, HttpSession session);
    boolean registered(UserVo userVo, HttpSession session);
    boolean loginIn(String email, String password);

}
