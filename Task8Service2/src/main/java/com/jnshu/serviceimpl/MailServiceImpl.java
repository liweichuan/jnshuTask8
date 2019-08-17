package com.jnshu.serviceimpl;

import com.jnshu.service.MailService;
import com.jnshu.util.AliMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("MailServiceImpl")
public class MailServiceImpl implements MailService {
    @Autowired
    AliMailUtil aliMailUtil;
    @Override
    public boolean sendMail(String emial, String message) throws IOException {
        return aliMailUtil.sendMail(emial,message);
    }
}
