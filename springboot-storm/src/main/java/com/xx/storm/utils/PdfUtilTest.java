package com.xx.storm.utils;

import java.io.File;
import java.util.List;

/**
 * <h1> PdfUtilTest </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/16
 */
public class PdfUtilTest {
    public static void main(String[] args) {

        //监控目录测试
//        Collection<File> pdf = FileUtils.listFiles(new File("D:/pdf"), FileFilterUtils.suffixFileFilter("pdf"), null);
//        for (File f:pdf) {
//            System.out.println(f.getName().substring(0, f.getName().lastIndexOf(".")));
//        }


        List<File> files = PdfToImgUtil.pdfToImage("D:/pdf/test1.pdf", 100);
        for (File f:files) {

            //绝对路径
            String path = f.getAbsolutePath();
            String s = path.replace("\\","/");

            //文件名
            System.out.println(f.getName().substring(0, f.getName().lastIndexOf(".")));

            //扩展名
            System.out.println(f.getName().substring(f.getName().lastIndexOf(".")+1));

            //大小
            System.out.println(f.length());


        }
    }
}
