package com.brenoleal.commons;

public class UnknownException extends RuntimeException {

    public UnknownException(String message, Throwable ex) {
        super(message, ex);
    }
}
