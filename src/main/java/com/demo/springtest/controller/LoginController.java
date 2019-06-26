package com.demo.springtest.controller;

import com.demo.springtest.base.BaseMessageResponse;
import com.demo.springtest.base.BaseResponseEntity;
import com.demo.springtest.base.BaseSuccessResponse;
import com.demo.springtest.dto.SingleValueDTO;
import com.demo.springtest.dto.UserProfileDTO;
import com.demo.springtest.exception.CommonException;
import com.demo.springtest.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class LoginController {
    @Autowired
    AuthenticationService mLoginService;


    @PostMapping("/login")
    public BaseResponseEntity login(@RequestBody UserProfileDTO account) {
        String token = mLoginService.login(account);
        if (token == null) {
            throw new CommonException("Wrong username / password");
        } else {
            return new BaseResponseEntity(new BaseSuccessResponse(new SingleValueDTO<>(token)), HttpStatus.OK);
        }
    }

    @PostMapping("/register")
    public BaseResponseEntity register(@RequestBody UserProfileDTO userProfileDTO) {
        boolean isRegisterSuccess = mLoginService.register(userProfileDTO);
        if (isRegisterSuccess) {
            return new BaseResponseEntity(new BaseSuccessResponse(new BaseMessageResponse("Success")), HttpStatus.OK);
        } else {
            throw new CommonException("Wrong username / password");
        }
    }
}
