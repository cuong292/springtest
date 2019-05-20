package com.demo.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
public class LoginController {
    @Autowired


    @PostMapping("/login")
    public String login(@PathVariable("user_name") String userName, @PathVariable("password") String password) {

    }
}
