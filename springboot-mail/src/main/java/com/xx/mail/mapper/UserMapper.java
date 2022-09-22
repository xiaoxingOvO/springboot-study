package com.xx.mail.mapper;

import com.xx.mail.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author xx
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-09-22 15:09:54
* @Entity com.xx.mail.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 注册，插入数据
     * @param user
     */
    @Select("insert into user (username,password,email) values (#{username},#{password},#{email})")
    void insertUser(User user);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    @Select("select * from user where email = #{email}")
    User queryByEmail(String email);


}




