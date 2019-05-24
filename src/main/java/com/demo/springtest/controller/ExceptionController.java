package com.demo.springtest.controller;

import com.demo.springtest.response.BaseError;
import com.demo.springtest.response.BaseResponse;
import com.demo.springtest.response.BaseResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Calendar;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public BaseResponseEntity handleException(Exception e) {
        BaseResponse baseResponse = new BaseResponse();
        BaseError error = new BaseError();
        error.setMessage(e.getMessage());
        error.setTimeStamp(Calendar.getInstance().getTimeInMillis());
        error.setStatus(HttpRes);
    }
}
