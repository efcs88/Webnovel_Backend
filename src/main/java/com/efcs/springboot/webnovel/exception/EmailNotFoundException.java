package com.efcs.springboot.webnovel.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message){
        super(message);
    }
}
