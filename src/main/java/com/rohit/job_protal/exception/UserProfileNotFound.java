package com.rohit.job_protal.exception;

public class UserProfileNotFound extends RuntimeException{
    public UserProfileNotFound(String message){
        super(message);
    }
}
