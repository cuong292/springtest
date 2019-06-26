package com.demo.springtest.dto;

public class SingleValueDTO<T> {
    private T K;

    public SingleValueDTO(T value) {
        this.K = value;
    }
}
