package com.itmaxglobal.bcmmigrationsync.util;

import com.itmaxglobal.bcmmigrationsync.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public void sendEmail(String subject, String templateName, Exception ex) {
        if(!emailFrom.isEmpty() && !emailTo.isEmpty()){
            emailService.sendEmail(emailFrom, emailTo, subject, templateName,
                    Objects.nonNull(ex) ? ex.getMessage() : "Test",
                    Objects.nonNull(ex) ? ex.getClass().getSimpleName() : "Test");
        }
    }
}