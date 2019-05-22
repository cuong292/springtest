package com.demo.springtest.controller;

import com.demo.springtest.entity.Account;
import com.demo.springtest.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class LoginController {
    @Autowired
    LoginService mLoginService;


    @PostMapping("/login")
    public String login(@RequestBody Account account) {
        return mLoginService.login(account);
    }
}
