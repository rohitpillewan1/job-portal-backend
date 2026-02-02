package com.rohit.job_protal.exception;

public class UnauthenticatedUser extends RuntimeException{
    public UnauthenticatedUser(String message){
        super(message);
    }
}
