package com.demo.springtest.response;

public class BaseResponse<T> {
    private T data;
    private BaseError error;

    public BaseResponse() {
    }

    public BaseResponse(T data, BaseError error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseError getError() {
        return error;
    }

    public void setError(BaseError error) {
        this.error = error;
    }
}
