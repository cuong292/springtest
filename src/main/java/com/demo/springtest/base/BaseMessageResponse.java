package com.demo.springtest.base;

public class BaseMessageResponse {
    private String message;

    public BaseMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
