package com.xx.itext5.utils;

import com.xx.itext5.enums.FileConst;
import com.xx.itext5.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            String fileName = "打印下载";
            response.setHeader(FileConst.CONTENT_DISPOSITION, FileConst.FILE_NAME + URLEncoder.encode(fileName + FileConst.PDF_EXTENSION, FileConst.UTF_8));
//            parseExcel2PdfOutputStream2(output, settlement, inspection,tripartite, usersign);
            output.flush();
            output.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
