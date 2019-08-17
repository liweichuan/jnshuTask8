package com.jnshu.service;

import java.io.IOException;

public interface MailService {
    boolean sendMail(String email, String message) throws IOException;
}
