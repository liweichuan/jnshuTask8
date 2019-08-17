package com.jnshu.util;

import org.springframework.stereotype.Component;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class LongDataTag extends TagSupport {
    private String value;
    @Override
    public int doStartTag() throws JspException {
        String vv = "" + value;
        try {
            //Long.valueOf,是将参数转换成long的包装类,即将string类型转化为long类型
            //trim()去掉字符串首尾空格 防止不必要的空格导致错误
            long time = Long.valueOf(vv.trim());

            // getInstance()是Calendar提供的一个类方法，它的作用是获得一个Calendar类型的通用对象
            Calendar c = Calendar.getInstance();

            //Calendar.setTimeInMillis(long) 方法从给定的long值设置日历的当前时间。
            c.setTimeInMillis(time * 1000);
            //创建日期格式化对象
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");

            //将日期转化成字符串
            String s = dateformat.format(c.getTime());
            //getOut()该方法返回一个JspWriter类的实例对象，也就是JSP内置对象--out对象，可以往客户端写入输入流
            pageContext.getOut().write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }
    public void setValue(String value) {//EL表达式使用set和get赋值和取值
        this.value = value;
    }

}