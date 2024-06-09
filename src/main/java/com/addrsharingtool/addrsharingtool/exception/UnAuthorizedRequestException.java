package com.addrsharingtool.addrsharingtool.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedRequestException extends RuntimeException {

    private Integer statusCode;

    public UnAuthorizedRequestException() {}

    public UnAuthorizedRequestException(String message) {
        super(message);
        this.statusCode = HttpStatus.UNAUTHORIZED.value();
    }

    public Integer getStatusCode() {
        return statusCode;
    }
    
}