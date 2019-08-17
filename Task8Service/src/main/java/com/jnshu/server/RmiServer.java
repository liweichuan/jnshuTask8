package com.jnshu.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiServer {
    private static final Logger logger= LogManager.getLogger(RmiServer.class);
    public static void main(String[] args) {
        logger.error("开始构建服务。。。。。");
        System.setProperty("java.rmi.server.hostname","101.132.124.42");
        new ClassPathXmlApplicationContext("ApplicationContext.xml");
        logger.error("已经开启服务。。。。。");
    }
}
