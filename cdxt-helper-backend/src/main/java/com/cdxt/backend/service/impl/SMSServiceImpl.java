package com.cdxt.backend.service.impl;

import com.cdxt.backend.service.SMSService;
import com.cdxt.common.enums.MailType;
import com.cdxt.common.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements SMSService {
    @Autowired
    EmailUtil emailUtil;

    @Override
    public Boolean sendMail(String email, String title, String content, MailType mailType) {
        return  emailUtil.send(email,content,title);
    }
}
