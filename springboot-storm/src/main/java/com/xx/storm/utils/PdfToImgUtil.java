package com.xx.storm.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1> PdfToImgUtil </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/16
 */
public class PdfToImgUtil {

    /**
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath  pdf完整路径
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @return 返回转换后图片集合list
     */
    public static List<File> pdfToImage(String PdfFilePath, int dpi) {

        File file = new File(PdfFilePath);
        String fileName = file.getName();


        //定义集合保存返回图片数据
        List<File> fileList = new ArrayList<File>();

        try {
            PDDocument pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);

            int pageCount = pdDocument.getNumberOfPages();

            for (int i = 0; i < pageCount; i++) {

                String pagePath = (file.getParent()+"\\"+fileName.substring(0, fileName.lastIndexOf(".")));
                String pageName = (fileName.substring(0, fileName.lastIndexOf(".")) +"-"+ String.valueOf(i) + ".png");

                File pageFile = null;
                File dir = new File(pagePath);

                if (dir.exists()) {
                    pageFile = new File(pagePath+"\\"+pageName);


                } else {
                    dir.mkdirs();
                    pageFile = new File(pagePath+"\\"+pageName);
                }

                BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                ImageIO.write(image, "png", pageFile);
                fileList.add(pageFile);
            }
            System.out.println("PDF文档转PNG图片成功！");
            return fileList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
