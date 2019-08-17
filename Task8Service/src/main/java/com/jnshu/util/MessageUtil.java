package com.jnshu.util;


import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 生成6位数的验证码的工具类
 */

@Component
public class MessageUtil {
    public String getMesgCode(){
        //取1-9的正整数
        Integer a=(new Random()).nextInt(9)+1;
        String message= String.valueOf(a);
        for (int i=0;i<5;i++){
            //使用concat将指定字符串连接到结尾
            message=message.concat(String.valueOf((int)(Math.random()*10)));
        }
        //把字符串转换成int返回
        return message;
    }
}


