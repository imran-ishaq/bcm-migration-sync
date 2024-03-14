package com.itmaxglobal.bcmmigrationsync.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "migration")
@Slf4j
public class MigrationController {

    @GetMapping(value = "/status")
    public ResponseEntity<Map<String, String>> status(){
        Map<String, String> res = new HashMap<>();
        res.put("message","Application is running...");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
