package com.demo.springtest.controller;

import com.demo.springtest.response.BaseError;
import com.demo.springtest.response.BaseResponse;
import com.demo.springtest.response.BaseResponseEntity;
import org.springframework.http.HttpStatus;
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
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new BaseResponseEntity(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public BaseResponseEntity handleException(NumberFormatException e) {
        BaseResponse baseResponse = new BaseResponse();
        BaseError error = new BaseError();
        error.setMessage("Number format wrong");
        error.setTimeStamp(Calendar.getInstance().getTimeInMillis());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new BaseResponseEntity(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
