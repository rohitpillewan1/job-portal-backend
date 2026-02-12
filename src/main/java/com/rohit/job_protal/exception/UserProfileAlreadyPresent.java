package com.rohit.job_protal.exception;

public class UserProfileAlreadyPresent extends RuntimeException{
    public UserProfileAlreadyPresent(String message){
        super(message);
    }
}
