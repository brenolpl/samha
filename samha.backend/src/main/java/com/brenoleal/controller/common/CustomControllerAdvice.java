package com.brenoleal.controller.common;

import com.brenoleal.commons.BusinessException;
import com.brenoleal.commons.ErrorResponse;
import com.brenoleal.commons.UnexpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
class CustomControllerAdvice {

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 404

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }


    @ExceptionHandler({BusinessException.class, NullPointerException.class})
    public ResponseEntity<ErrorResponse> handleInternalException(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace // specifying the stack trace in case of 500s
                ),
                status
        );
    }
}