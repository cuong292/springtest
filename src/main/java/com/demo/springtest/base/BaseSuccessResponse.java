package com.demo.springtest.base;

public class BaseSuccessResponse<T> extends BaseResponse<T> {
    public BaseSuccessResponse(T data) {
        super(data, null);
    }
}
