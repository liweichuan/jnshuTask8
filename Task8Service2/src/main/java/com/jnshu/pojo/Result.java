package com.jnshu.pojo;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**这个类用来返回信息给页面
 * @code返回状态码
 * @message表示返回的信息
 * @data是用来接受对象
 * */


@Component
public class Result implements Serializable {
    private String code;
    private String message;
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
