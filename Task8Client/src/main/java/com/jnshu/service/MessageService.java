package com.jnshu.service;
import org.mybatis.spring.annotation.MapperScan;
import com.aliyuncs.exceptions.ClientException;


@MapperScan
public interface MessageService {
    boolean sendMesg(String phone, String message) throws ClientException;
}
