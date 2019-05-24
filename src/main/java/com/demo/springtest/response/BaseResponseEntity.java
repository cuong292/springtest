package com.demo.springtest.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseResponseEntity extends ResponseEntity<BaseResponse> {
    public BaseResponseEntity(BaseResponse body, HttpStatus status) {
        super(body, status);
    }
}
