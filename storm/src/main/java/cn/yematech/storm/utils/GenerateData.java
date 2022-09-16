package cn.yematech.storm.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * <h1> GenerateData </h1>
 * <pre>
 *  制造一个假数据
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/15
 */
public class GenerateData {
    public static void main(String[] args) {
        // 1.创建文件
        File file = new File("D:/pdf/test1.md");
        // 2.制造数据
        StringBuffer item = new StringBuffer();
        for (int i = 0;i <= 100; i++){
            item.append(i+"\r\n");
        }

        // 3.数据写入文件
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(item.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println("成功！");

        } catch (Exception e) {

        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
