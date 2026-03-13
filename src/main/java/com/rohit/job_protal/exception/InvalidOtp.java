package com.rohit.job_protal.exception;

public class InvalidOtp extends RuntimeException{
    public InvalidOtp(String message){
        super(message);
    }
}
