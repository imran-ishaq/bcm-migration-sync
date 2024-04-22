package com.itmaxglobal.bcmmigrationsync.controller;

import com.itmaxglobal.bcmmigrationsync.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.itmaxglobal.bcmmigrationsync.util.Constants.EMAIL_SUBJECT;
import static com.itmaxglobal.bcmmigrationsync.util.Constants.EMAIL_TEMPLATE_NAME;

@RestController
@RequestMapping(value = "migration")
@Slf4j
public class MigrationController {

    @Autowired
    ApplicationContext appContext;

    private final EmailUtil emailUtil;

    @Autowired
    public MigrationController(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Map<String, String>> status(){
        Map<String, String> res = new HashMap<>();
        res.put("message","Application is running...");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping(value = "/check-smtp")
    public ResponseEntity<?> testEmail(){
        emailUtil.sendEmail(EMAIL_SUBJECT, EMAIL_TEMPLATE_NAME, null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
