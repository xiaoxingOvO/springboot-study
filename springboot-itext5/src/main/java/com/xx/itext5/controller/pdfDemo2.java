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
public class pdfDemo2 {
    @GetMapping("/print2")
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
        PdfPTable table = new PdfPTable(3); // 3列表格

        PdfPCell cell; // 单元格
//        cell = new PdfPCell(new Phrase("row 1; cell 1", getChineseFont()));
//        cell.setBackgroundColor(RED);
//        cell.setRowspan(3);
        cell = mergeColAndRow("AAAA",getChineseFont(),1,3);
        table.addCell(cell);

//        cell = new PdfPCell(new Phrase("row 1; cell 2", getChineseFont()));
//        cell.setRowspan(2);
//        cell.setBackgroundColor(RED);
        cell = mergeColAndRow("BBBB",getChineseFont(),1,2);
        table.addCell(cell);

//        cell = new PdfPCell(new Phrase("row 1; cell 3", getChineseFont()));
//        cell.setBackgroundColor(RED);
        cell = mergeColAndRow("CCCC",getChineseFont(),1,1);
        table.addCell(cell);

//        cell = new PdfPCell(new Phrase("row 2; cell 3", getChineseFont()));
//        cell.setBackgroundColor(RED);
//        cell.setRowspan(3);
        cell = mergeColAndRow("DDDD",getChineseFont(),1,3);
        table.addCell(cell);

//        cell = new PdfPCell(new Phrase("row 3; cell 2", getChineseFont()));
//        cell.setBackgroundColor(RED);
        cell = mergeColAndRow("EEEE",getChineseFont(),1,1);
        table.addCell(cell);

//        cell = new PdfPCell(new Phrase("row 4; cell 1", getChineseFont()));
//        cell.setBackgroundColor(RED);
        cell = mergeColAndRow("FFFF",getChineseFont(),1,1);
        table.addCell(cell);

//        cell = new PdfPCell(new Phrase("row 4; cell 2", getChineseFont()));
//        cell.setBackgroundColor(RED);
        cell = mergeColAndRow("GGGG",getChineseFont(),1,1);
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


    /**
     *
     * @param str  内容
     * @param font  字体
     * @param i 列
     * @param j 行
     * @return cell
     */
     public static PdfPCell mergeColAndRow(String str, Font font, int i, int j) {
     PdfPCell cell = new PdfPCell(new Paragraph(str, font));
     cell.setMinimumHeight(25); cell.setHorizontalAlignment(Element.ALIGN_CENTER);
     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
     cell.setColspan(i);
     cell.setRowspan(j);
     return cell;
     }

}
