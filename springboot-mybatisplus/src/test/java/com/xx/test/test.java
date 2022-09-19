package com.xx.test;

import com.xx.mybatis.MybatisApplication;
import com.xx.mybatis.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <h1> test </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/18
 */
@SpringBootTest(classes = MybatisApplication.class)
public class test {

    @Autowired
    private TestService testService;

    @Test
    public void test1(){
        List<com.xx.mybatis.pojo.Test> tests = testService.selectAllUser();
        for (com.xx.mybatis.pojo.Test t:tests) {
            System.out.println(t);
        }
    }
}
