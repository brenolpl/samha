package com.brenoleal.commons;

public class BusinessException extends RuntimeException{
    public BusinessException(String message, Throwable err) {
        super(message, err);
    }
}
