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
import java.io.IOException;

/**
 * @author xx
 * @date 2022/12/30
 */
@RestController
@RequestMapping("/pdf")
@Api("pdf预览")
public class pdfDemo3 {
    @GetMapping("/print3")
    @ApiOperation("demo")
    public void pdfPrint(HttpServletResponse response) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        // 使用PDFWriter进行写文件操作
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // 中文字体(现在高版本的不支持中文包)
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);// 中文、12、正常

        int colNumber = 6;

        // 跨行跨列表格
        PdfPTable table = new PdfPTable(4); // 3列表格
        PdfPCell cell; // 单元格
        cell = new PdfPCell(new Phrase("row 1; cell 1", getChineseFont()));
        cell.setRowspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("row 1; cell 2", getChineseFont()));
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("row 1; cell 3", getChineseFont()));
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("row 1; cell 4", getChineseFont()));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("row 2; cell 4",getChineseFont()));
        cell.setRowspan(3);
        table.addCell(cell);

        table.addCell(cell);
        cell = new PdfPCell(new Phrase("row 3; cell 2", getChineseFont()));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("row 3; cell 3", getChineseFont()));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("row 4; cell 1", getChineseFont()));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("row 4; cell 2", getChineseFont()));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("row 4; cell 3", getChineseFont()));
        table.addCell(cell);

        document.add(table);
        document.close();
    }

    public Font getChineseFont() {
        BaseFont bfChinese;
        Font fontChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // fontChinese = new Font(bfChinese, 12, Font.NORMAL);
            fontChinese = new Font(bfChinese, 12, Font.NORMAL, BaseColor.BLUE);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;

    }

}
