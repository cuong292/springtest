package com.demo.springtest.controller;

import com.demo.springtest.base.BaseMessageResponse;
import com.demo.springtest.base.BaseResponseEntity;
import com.demo.springtest.base.BaseSuccessResponse;
import com.demo.springtest.dto.AccountDTO;
import com.demo.springtest.dto.SingleValueDTO;
import com.demo.springtest.dto.UserProfileDTO;
import com.demo.springtest.exception.CommonException;
import com.demo.springtest.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/authorization")
public class LoginController {
    @Autowired
    AuthenticationService mLoginService;


    @PostMapping("/login")
    public BaseResponseEntity login(@RequestBody AccountDTO account) {
        String token = mLoginService.login(account);
        return new BaseResponseEntity(new BaseSuccessResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public BaseResponseEntity register(@RequestBody UserProfileDTO userProfileDTO) {
        boolean isRegisterSuccess = mLoginService.register(userProfileDTO);
        if (isRegisterSuccess) {
            return new BaseResponseEntity(new BaseSuccessResponse(new BaseSuccessResponse("We sent you a verify email,please verify your account to login")), HttpStatus.OK);
        } else {
            throw new CommonException("Wrong username / password");
        }
    }

    @PostMapping("/resetpassword")
    public BaseResponseEntity resetPassword(@PathVariable String email) {
        boolean isResetSuccess = mLoginService.resetPassword(email);
        if (isResetSuccess) {
            return new BaseResponseEntity(new BaseSuccessResponse(new BaseSuccessResponse("We sent you a reset password email")), HttpStatus.OK);
        } else {
            throw new CommonException("Wrong username / password");
        }
    }

    @GetMapping("/reset_token")
    public ModelAndView redirect(@PathVariable String email) {
        mLoginService.saveUserResetPassword(email);
        return new ModelAndView("redirect:https://google.com.vn");
    }
}
