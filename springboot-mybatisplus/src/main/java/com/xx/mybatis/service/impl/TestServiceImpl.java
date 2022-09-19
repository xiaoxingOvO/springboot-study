package com.xx.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.mybatis.pojo.Test;
import com.xx.mybatis.service.TestService;
import com.xx.mybatis.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 79340
* @description 针对表【test】的数据库操作Service实现
* @createDate 2022-09-18 20:16:02
*/
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
    implements TestService{

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> selectAllUser() {
        return testMapper.selectAllUser();
    }

    @Override
    public Test selectUserById(Integer id) {
        return selectUserById(id);
    }
}




