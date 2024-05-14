package com.itmaxglobal.bcmmigrationsync.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.itmaxglobal.bcmmigrationsync.util.Constants.*;

@Service
@Slf4j
public class EmailService {

    @Value("${com.bcm.host-name}")
    private String hostName;

    @Value("${com.bcm.host-address}")
    private String hostAddress;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String emailFrom, String emailTo, String subject, String templateName, String exceptionMessage, String exceptionClassName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            String[] setCCs = new String[]{IMRAN_ITMAX_EMAIL, ZAIN_ITMAX_EMAIL, LARA_ITMAX_EMAIL, MOHIT_ITMAX_EMAIL,
                    GABY_ITMAX_EMAIL, CHRISTIAN_ITMAX_EMAIL, CHARBEL_ITMAX_EMAIL};

            Context context = new Context();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            String date = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            context.setVariable("date", date);
            context.setVariable("hostName", hostName);
            context.setVariable("address", hostAddress);
            context.setVariable("exceptionClassName", exceptionClassName);
            context.setVariable("reason", exceptionMessage);

            helper.setFrom(emailFrom);
            helper.setTo(emailTo);
            helper.setSubject(subject);
//            helper.setCc(setCCs);

            String html = templateEngine.process(templateName, context);

            helper.setText(html,true);
            javaMailSender.send(message);

            log.info("Mail Sent Successfully");

        } catch (MessagingException e){
            log.info("Exception from EmailService()");
            log.error(e.getMessage());
        }
    }
}