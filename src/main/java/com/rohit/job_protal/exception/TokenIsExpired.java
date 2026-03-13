package com.rohit.job_protal.exception;

public class TokenIsExpired extends RuntimeException{
    public TokenIsExpired(String message){
        super(message);
    }
}
