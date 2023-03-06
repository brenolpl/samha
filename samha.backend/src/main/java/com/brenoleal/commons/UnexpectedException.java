package com.brenoleal.commons;

public class UnexpectedException extends RuntimeException{
    public UnexpectedException(String message, Throwable err) {
        super(message, err);
    }
}
