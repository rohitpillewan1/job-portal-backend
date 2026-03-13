package com.rohit.job_protal.exception;

public class TokenIsUsed extends RuntimeException{
    public TokenIsUsed(String message){
        super(message);
    }
}
