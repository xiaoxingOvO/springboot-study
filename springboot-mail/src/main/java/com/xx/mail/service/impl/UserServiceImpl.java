package com.xx.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.mail.pojo.User;
import com.xx.mail.pojo.UserVo;
import com.xx.mail.pojo.UserVoToUser;
import com.xx.mail.service.UserService;
import com.xx.mail.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
* @author xx
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-09-22 15:09:54
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserMapper userMapper;

    //application.properties中已配置的值
    @Value("${spring.mail.username}")
    private String from;

    /**
     * @description: 给前端发送验证码
     * @author: xiaoxing
     * @date: 2022/9/22
     * @param: [email, session]
     * @return: boolean
     **/
    @Override
    public boolean sendMimeMail(String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            //主题
            mailMessage.setSubject("验证码邮件");
            //生成随机数
            String code = randomCode();

            //将随机数放置到session中
            session.setAttribute("email",email);
            session.setAttribute("code",code);

            //内容
            mailMessage.setText("您收到的验证码是："+code);

            //发给谁
            mailMessage.setTo(email);

            //你自己的邮箱
            mailMessage.setFrom(from);

            //发送
            mailSender.send(mailMessage);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 随机生成6位数的验证码
     * @return String code
     */
    public String randomCode(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


    /**
     * @description: 检验验证码是否一致
     * @author: xiaoxing
     * @date: 2022/9/22
     * @param: [userVo, session]
     * @return: boolean
     **/
    @Override
    public boolean registered(UserVo userVo, HttpSession session) {
        //获取session中的验证信息
        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");

        //获取表单中的提交的验证信息
        String voCode = userVo.getCode();

        //如果email数据为空，或者不一致，注册失败
        if (email == null || email.isEmpty()){
            //return "error,请重新注册";
            return false;
        }else if (!code.equals(voCode)){
            //return "error,请重新注册";
            return false;
        }

        //保存数据
        User user = UserVoToUser.toUser(userVo);

        //将数据写入数据库
        userMapper.insertUser(user);

        //跳转成功页面
        return true;
    }

    /**
     * @description: 过输入email查询password，然后比较两个password，如果一样，登录成功
     * @author: xiaoxing
     * @date: 2022/9/22
     * @param: [email, password]
     * @return: boolean
     **/
    @Override
    public boolean loginIn(String email, String password) {
        User user = userMapper.queryByEmail(email);

        if(!user.getPassword().equals(password)){
            return false;
        }
        System.out.println("登录成功:数据库密码是："+user.getPassword());
        return true;
    }

}




