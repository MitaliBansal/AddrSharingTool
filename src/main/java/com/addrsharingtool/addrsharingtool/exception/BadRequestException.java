package com.addrsharingtool.addrsharingtool.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    private Integer statusCode;
    private final String message;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
        this.statusCode = HttpStatus.BAD_REQUEST.value();
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
    
}