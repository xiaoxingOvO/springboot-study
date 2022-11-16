package com.xx.craw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class CrawApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(CrawApplication.class, args);
//        Environment env = application.getEnvironment();
//        String ip = InetAddress.getLocalHost().getHostAddress();
//        String port = env.getProperty("server.port");
//        String property = env.getProperty("server.servlet.context-path");
//        String path = property == null ? "" : property;
//
//        System.out.println(
//                "\n" +
//                        "----------------------------------------------------------\n\t" +
//                        "Application is running! Access URLs:\n\t" +
//                        "Local: \t\thttp://localhost:" + port + path + "\n\t" +
//                        "External: \thttp://" + ip + ":" + port + path + "\n" +
//                        "----------------------------------------------------------\n");
    }

}
