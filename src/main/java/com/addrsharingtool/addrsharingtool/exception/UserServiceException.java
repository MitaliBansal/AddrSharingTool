package com.addrsharingtool.addrsharingtool.exception;

public class UserServiceException extends RuntimeException {

    public UserServiceException() {}

    public UserServiceException(String message) {
        super(message);
    }

}