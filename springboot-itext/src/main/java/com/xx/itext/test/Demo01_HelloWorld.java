package com.xx.itext.test;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.IOException;

/**
 * @author xx
 * @date 2022/12/30
 */
public class Demo01_HelloWorld {

    public static final String PATH = "/Users/xiaoxing/projects/springboot-study/springboot-itext/src/main/resources/pdf/helloworld.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(PATH);
        file.getParentFile().mkdirs();
        new Demo01_HelloWorld().createPdf(PATH);
    }

    public void createPdf(String path) throws IOException {
        PdfWriter pdfWriter = new PdfWriter(path);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(pdfWriter);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4);

        //Add paragraph to the document
        document.add(new Paragraph("Hello World!"));

        //Close document
        document.close();
    }
}
