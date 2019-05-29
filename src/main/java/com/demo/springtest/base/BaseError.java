package com.demo.springtest.base;

import org.springframework.http.ResponseEntity;

import java.util.Date;

public class BaseError {
    private int status;
    private String message;
    private long timeStamp;

    public BaseError() {
    }

    public BaseError(String message, int status) {
        this.status = status;
        this.message = message;
        this.timeStamp = new Date().getTime();
    }

    public BaseError(String message) {
        this.message = message;
        this.timeStamp = new Date().getTime();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
