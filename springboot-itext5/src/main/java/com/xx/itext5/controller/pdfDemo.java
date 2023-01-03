package com.xx.itext5.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author xx
 * @date 2022/12/30
 */
@RestController
@RequestMapping("/pdf")
@Api("pdf预览")
public class pdfDemo {
    @GetMapping("/print")
    @ApiOperation("demo")
    public void caseMixPrint(HttpServletResponse response) {
        try {
            //设置中文
            BaseFont bfComic = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //字体
            Font font1 = new Font(bfComic, 8, Font.NORMAL);
            //字体
            Font font2 = new Font(bfComic, 15, Font.NORMAL);
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/pdf");
            response.setCharacterEncoding("utf-8");
            // 下载文件的默认名称
            Paragraph par = new Paragraph("这是标题啊" + "\n\n", new Font(bfComic, 18, Font.BOLD));
            //设置局中对齐
            par.setAlignment(Element.ALIGN_CENTER);
            PdfPCell pdfPCell19 = new PdfPCell(par);
            Document document = new Document();
            //将纸张设置为A4纸
            document.setPageSize(PageSize.A4);
            //设置上下左右边距
            //document.setMargins(35,30,0,0);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(par);
            //每列宽度
            int widths[] = {10, 10, 10, 10};
            //列
            for (int i = 0 ;i < 5;i++){
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(98);
                PdfPCell pdfPCell = new PdfPCell();
                table.setWidths(widths);


                pdfPCell = new PdfPCell(new Paragraph("统计日期:" + new Date(), font2));
                pdfPCell.setVerticalAlignment(Element.ALIGN_LEFT);
                pdfPCell.setPaddingBottom(20);
                share(pdfPCell);
                pdfPCell.setColspan(2);
                table.addCell(pdfPCell);


                pdfPCell = new PdfPCell(new Paragraph("打印日期:" + new Date(), font2));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                pdfPCell.setVerticalAlignment(Element.ALIGN_RIGHT);
                pdfPCell.setPaddingBottom(20);
                pdfPCell.setPaddingLeft(20);
                share(pdfPCell);
                pdfPCell.setColspan(2);
                table.addCell(pdfPCell);

                /** 第一行 表头*/
                pdfPCell = new PdfPCell(new Paragraph("编号", font1));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setBorderWidthRight(0);
                table.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph("姓名", font1));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setBorderWidthRight(0);
                pdfPCell.setFixedHeight(20);
                table.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph("性别", font1));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setBorderWidthRight(0);
                pdfPCell.setFixedHeight(20);
                table.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph("年龄", font1));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(20);
                table.addCell(pdfPCell);


                /** 添加数据*/
                pdfPCell = new PdfPCell(new Paragraph("01", font1));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setBorderWidthRight(0);
                pdfPCell.setBorderWidthTop(0);
                table.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph("张三", font1));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setBorderWidthRight(0);
                pdfPCell.setBorderWidthTop(0);
                pdfPCell.setFixedHeight(20);
                table.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph("男", font1));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pdfPCell.setBorderWidthRight(0);
                pdfPCell.setBorderWidthTop(0);
                pdfPCell.setFixedHeight(20);
                table.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph("12", font1));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(20);
                pdfPCell.setBorderWidthTop(0);
                table.addCell(pdfPCell);

                document.add(table);
            }
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void share(PdfPCell pdfPCell) {
        //去除上下左右的边框
        pdfPCell.setBorderWidthRight(0);
        pdfPCell.setBorderWidthBottom(0);
        pdfPCell.setBorderWidthLeft(0);
        pdfPCell.setBorderWidthTop(0);
    }


}
