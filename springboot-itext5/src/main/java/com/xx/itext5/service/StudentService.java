package com.xx.itext5.service;

import com.xx.itext5.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author xiaoxing
* @description 针对表【student】的数据库操作Service
* @createDate 2022-12-30 17:50:25
*/
public interface StudentService extends IService<Student> {

    Student getStudentById(String studentId);

}