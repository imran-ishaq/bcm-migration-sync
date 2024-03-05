package com.itmaxglobal.bcmmigrationsync.controller;

import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Session;
import com.itmaxglobal.bcmmigrationsync.bcmv2.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/session")
public class SessionController {

    @Autowired
    SessionRepository sessionRepository;


    @GetMapping(value = "/list")
    public Session getSessionList(){
        return sessionRepository.findById(2L).get();
    }
}
