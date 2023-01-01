package com.xx.itext5.utils;

import com.xx.itext5.enums.FileConst;
import com.xx.itext5.pojo.Student;
import com.xx.itext5.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;


/**
 * @author xx
 * @date 2022/12/30
 */
public class StudentUtil {

    private static final Logger log = LoggerFactory.getLogger(StudentUtil.class);

    /**
     * 打印excel模板
     * @param response
     * @param studentId
     * @return
     */
    public static String printPdf(HttpServletResponse response, String studentId) {
        StudentService studentService = SpringUtil.getBean(StudentService.class);
        try {
            Student student = studentService.getStudentById(studentId);
            return FileUtil.printPdf(response, student);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            response.reset();
            return FileConst.SCRIPT_PREFIX
                    .concat(String.format(FileConst.STRING_FORMAT, e.getMessage()))
                    .concat(FileConst.SCRIPT_SUFFIX);
        }
    }

}
