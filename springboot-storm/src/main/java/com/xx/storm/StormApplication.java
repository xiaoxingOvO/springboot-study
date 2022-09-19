package com.xx.storm;

import com.xx.storm.service.pdf.PdfTopology;
import com.xx.storm.utils.GetSpringBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <h1> StormApplication </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/9/17
 */
@SpringBootApplication
public class StormApplication {

    public static void main(String[] args) throws Exception {
        //先启动Storm，后启动springboot的工程
        PdfTopology pdfTopology = new PdfTopology();
        pdfTopology.runStorm(args);
        //启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        ConfigurableApplicationContext context = SpringApplication.run(StormApplication.class, args);
        GetSpringBean springBean=new GetSpringBean();
        springBean.setApplicationContext(context);
    }

    public static void run(String args) {
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        ConfigurableApplicationContext context = SpringApplication.run(StormApplication.class, args);
        GetSpringBean springBean=new GetSpringBean();
        springBean.setApplicationContext(context);
    }
}
