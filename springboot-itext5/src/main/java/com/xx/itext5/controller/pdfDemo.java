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
            Font font1 = new Font(bfComic, 12, Font.NORMAL);
            // 告诉浏览器用什么软件可以打开此文件
            response.setContentType("application/pdf; charset=utf-8");
            Document document = new Document();
            //将纸张设置为A4纸
            document.setPageSize(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            // 下载文件的默认名称
            Paragraph par = new Paragraph("这是标题啊" + "\n\n", new Font(bfComic, 18, Font.BOLD));
            //设置局中对齐
            par.setAlignment(Element.ALIGN_CENTER);
            document.add(par);
            //列
            PdfPTable table = new PdfPTable(4);
            PdfPCell pdfPCell;

            pdfPCell = new PdfPCell(new Paragraph("编号", font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPCell.setRowspan(3);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("姓名", font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPCell.setRowspan(2);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("性别", font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPCell.setRowspan(2);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("年龄", font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("12", font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setRowspan(3);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("3,2",font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(pdfPCell);
            pdfPCell = new PdfPCell(new Paragraph("3,3",font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("01", font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("张三", font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("男", font1));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(pdfPCell);

            document.add(table);
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
