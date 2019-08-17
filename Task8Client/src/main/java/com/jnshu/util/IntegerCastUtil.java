package com.jnshu.util;

import org.springframework.stereotype.Component;

@Component
public class IntegerCastUtil {

    //integer转成string
    public String integerCastString(Object obj){
        return (obj == null )? null:obj.toString();
    }
    //string转成integer
    public Integer stringCastInteger(String str){
        Integer i=null;
        if (str!=null){
            i=Integer.valueOf(str);
        }
        return i;
    }
}
