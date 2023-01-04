package com.xx.itext5.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.itext5.pojo.Student;
import com.xx.itext5.service.StudentService;
import com.xx.itext5.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author xiaoxing
* @description 针对表【student】的数据库操作Service实现
* @createDate 2022-12-30 17:50:25
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{


    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student getStudentById(String studentId) {
        return studentMapper.selectById(studentId);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentMapper.selectList(null);
    }
}




