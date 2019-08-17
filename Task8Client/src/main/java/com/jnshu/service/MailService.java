package com.jnshu.service;
import org.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
@MapperScan
public interface MailService {
    boolean sendMail(String email, String message) throws IOException;
}
