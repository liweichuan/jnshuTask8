package com.jnshu.service;

import com.aliyuncs.exceptions.ClientException;

public interface MessageService {
    boolean sendMesg(String phone, String message) throws ClientException;
}
