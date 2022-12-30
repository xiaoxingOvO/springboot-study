package com.xx.itext5.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author xx
 * @date 2022/12/30
 */
public class FileUtil {

    /**
     * 打印入库单\供货单
     *
     * @param response   响应对象
     * @param settlement 结算单ID
     * @param usersign
     * @return result
     */
//    public static String printStorageOrSupplyPdf(HttpServletResponse response, SettlementDetailRo settlement, List<InspectionRo> inspection,
//                                                 Tripartite tripartite, String usersign) throws Exception {
//        try {
//            OutputStream output = response.getOutputStream();
//            response.reset();
//            response.setContentType(FileConst.APPLICATION_PDF);
//            // 构建供应商文件名
//            String fileName = generateStorageOrSupplyFileName(settlement, tripartite);
//            response.setHeader(FileConst.CONTENT_DISPOSITION, FileConst.FILE_NAME + URLEncoder.encode(fileName + FileConst.PDF_EXTENSION, FileConst.UTF_8));
//
//            parseExcel2PdfOutputStream2(output, settlement, inspection,tripartite, usersign);
////            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////            parseExcel2PdfOutputStream(outputStream, settlement, tripartite,usersign);
////            doExcelToPdf(output, outputStream, null);
//            output.flush();
//            output.close();
//        } catch (IOException e) {
//            log.error(e.getMessage());
//            e.printStackTrace();
//            throw new BizException(ErrorConstants.ERR_SERVER_ERROR);
//        }
//        return null;
//    }
}
