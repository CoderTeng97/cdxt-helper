package com.cdxt.backend.deploy.service;

import com.cdxt.common.enums.MailType;

public interface SMSService {
    Boolean sendMail(String email, String title, String content, MailType mailType);
}
