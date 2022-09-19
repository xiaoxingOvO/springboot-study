package com.xx.mybatis.service;

import com.xx.mybatis.pojo.Test;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 79340
* @description 针对表【test】的数据库操作Service
* @createDate 2022-09-18 20:16:02
*/
public interface TestService extends IService<Test> {

    List<Test> selectAllUser();

    Test selectUserById(Integer id);

}
