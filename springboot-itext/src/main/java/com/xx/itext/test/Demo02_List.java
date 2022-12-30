package com.xx.itext.test;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.sun.javafx.font.FontConstants;
import java.io.File;
import java.io.IOException;


/**
 * @author xx
 * @date 2022/12/30
 */
public class Demo02_List {

    public static final String PATH = "/Users/xiaoxing/projects/springboot-study/springboot-itext/src/main/resources/pdf/helloworld.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(PATH);
        file.getParentFile().mkdirs();
        new Demo01_HelloWorld().createPdf(PATH);
    }

//    public void createPdf(String path) throws IOException {
//        PdfWriter pdfWriter = new PdfWriter(path);
//
//        //Initialize PDF document
//        PdfDocument pdf = new PdfDocument(pdfWriter);
//
//        // Initialize document
//        Document document = new Document(pdf, PageSize.A4);
//
//        // Create a PdfFont
//        PdfFont font = PdfFontFactory.createFont(FontConstants.);
//        // Add a Paragraph
//        document.add(new Paragraph("iText is:").setFont(font));
//        // Create a List
//        List list = new List()
//                .setSymbolIndent(12)
//                .setListSymbol("\u2022")
//                .setFont(font);
//        // Add ListItem objects
//        list.add(new ListItem("Never gonna give you up"))
//                .add(new ListItem("Never gonna let you down"))
//                .add(new ListItem("Never gonna run around and desert you"))
//                .add(new ListItem("Never gonna make you cry"))
//                .add(new ListItem("Never gonna say goodbye"))
//                .add(new ListItem("Never gonna tell a lie and hurt you"));
//        // Add the list
//        document.add(list);
//
//        //Close document
//        document.close();
//    }
}
