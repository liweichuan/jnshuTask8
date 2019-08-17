package com.jnshu.util;

import org.springframework.stereotype.Component;

/**
 * 这个类就是为了将token的id提取出来并转成long
 * */
@Component
public class StringUtil {
    public static long tool(String token){
        String id=token.substring(0,token.indexOf("/"));//取出字符串中的下标0到“/”的下标
        return Long.parseLong(id);//将字符串的id转换成long型
    }
}
