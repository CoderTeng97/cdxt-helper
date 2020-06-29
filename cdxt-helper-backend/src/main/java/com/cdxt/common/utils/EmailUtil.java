package com.cdxt.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 邮箱发送工具
 */
@Component
public class EmailUtil {

    @Value("${email.config.originEmail}")
    private String originEmail;
    @Value("${email.config.secret}")
    private String secret;
    @Value("${email.config.host}")
    private String host;

    public boolean send(String email, String content, String title) {
        // MLHXURFPCUVBDGUG
        String to = email;
        String from = originEmail;

        try {
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.put("mail.smtp.auth", "true");
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(originEmail, secret); //
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //设置邮件标题
            message.setSubject(title);
            //设置邮件内容
            message.setText(content);
            Transport.send(message);
            return true;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return false;

        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }
}
