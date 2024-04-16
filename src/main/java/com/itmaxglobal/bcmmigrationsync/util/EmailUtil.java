package com.itmaxglobal.bcmmigrationsync.util;

import com.itmaxglobal.bcmmigrationsync.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil {
    private final EmailService emailService;

    @Value("${com.bcm.email.to}")
    private String emailTo;

    @Value("${com.bcm.email.from}")
    private String emailFrom;

    @Autowired
    public EmailUtil(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendEmail(String subject, String templateName, Exception ex) throws MessagingException {
        if(!emailFrom.isEmpty()){
            emailService.sendEmail(emailFrom, emailTo, subject, templateName, ex.getMessage(), ex.getClass().getSimpleName());
        }
    }
}