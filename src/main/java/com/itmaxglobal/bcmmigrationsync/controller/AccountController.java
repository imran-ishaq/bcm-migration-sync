package com.itmaxglobal.bcmmigrationsync.controller;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv1.repository.AccountRepository;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping(value = "/obj")
    public Account getAccount(){
        return null;
    }

}
