package com.itmaxglobal.bcmmigrationsync.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/api/migration")
@Slf4j
public class MigrationController {

    @PostMapping(value = "/status")
    public ResponseEntity<?> status(){
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>().put("message","Application is running..."));
    }
}
