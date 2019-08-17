package com.jnshu.serviceimpl;

import com.aliyuncs.exceptions.ClientException;
import com.jnshu.service.MessageService;
import com.jnshu.util.AliSmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("MessageServiceImpl")
public class MessageServiceImpl implements MessageService {
    @Autowired
    AliSmsUtil aliSmsUtil;
    @Override
    public boolean sendMesg(String phone, String message) throws ClientException {
        return aliSmsUtil.sendMesg(phone,message);
    }
}
