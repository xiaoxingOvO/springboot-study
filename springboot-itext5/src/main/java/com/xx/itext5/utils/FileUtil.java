package com.xx.itext5.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xx.itext5.Excel.StudentExcel;
import com.xx.itext5.enums.FileConst;
import com.xx.itext5.pojo.Student;
import com.xx.itext5.utils.excel.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author xx
 * @date 2022/12/30
 */
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 打印
     *
     * @param response   响应对象
     * @param student 学生对象
     */
    public static String printPdf(HttpServletResponse response, Student student) throws Exception {
        try {
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setContentType(FileConst.APPLICATION_PDF);
            // 构建文件名
//            String fileName = generateStorageOrSupplyFileName(settlement, tripartite);
            String fileName = "studnet信息模版";
            response.setHeader(FileConst.CONTENT_DISPOSITION, FileConst.FILE_NAME + URLEncoder.encode(fileName + FileConst.PDF_EXTENSION, FileConst.UTF_8));
            ExcelUtil.fillExcel2Pdf(new ClassPathResource(FileConst.Student_PATH).getInputStream(),
                    FileConst.SHEET_NAME, getStudentExcel(student), output,false);
            output.flush();
            output.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }


    /**
     * 构建学生数据对象
     *
     * @param student
     * @return result
     */
    public static StudentExcel getStudentExcel(Student student) throws JsonProcessingException {
        // 设置主体数据
        StudentExcel studentExcel = new StudentExcel();
        studentExcel.setName(student.getName());
        studentExcel.setAge(studentExcel.getAge());
        studentExcel.setHobby(studentExcel.getHobby());
        studentExcel.setWork(studentExcel.getWork());
        studentExcel.setPhone(studentExcel.getPhone());
        studentExcel.setEmail(studentExcel.getEmail());
        studentExcel.setBz(student.getBz());
        return studentExcel;
    }

}
