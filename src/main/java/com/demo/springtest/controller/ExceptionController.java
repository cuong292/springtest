package com.demo.springtest.controller;

import com.demo.springtest.exception.CommonException;
import com.demo.springtest.base.BaseError;
import com.demo.springtest.base.BaseResponse;
import com.demo.springtest.base.BaseResponseEntity;
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
        baseResponse.setError(error);
        return new BaseResponseEntity(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CommonException.class)
    public BaseResponseEntity handleException(CommonException e) {
        BaseResponse baseResponse = new BaseResponse();
        BaseError error = new BaseError(e.getMessage(), HttpStatus.OK.value());
        baseResponse.setData(null);
        baseResponse.setError(error);
        return new BaseResponseEntity(baseResponse, HttpStatus.OK);
    }
}
