package com.demo.springtest.controller;

import com.demo.springtest.base.BaseResponseEntity;
import com.demo.springtest.base.BaseSuccessResponse;
import com.demo.springtest.exception.NotFoundException;
import com.demo.springtest.entity.Account;
import com.demo.springtest.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class LoginController {
    @Autowired
    LoginService mLoginService;


    @PostMapping("/login")
    public BaseResponseEntity login(@RequestBody Account account) {
        String token = mLoginService.login(account);
        if (token == null) {
            throw new NotFoundException("Wrong username / password");
        } else {
            return new BaseResponseEntity(new BaseSuccessResponse(token), HttpStatus.OK);
        }
    }
}
