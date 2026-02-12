package com.rohit.job_protal.exception;

public class UserExperienceAlreadyPresent extends RuntimeException{
    public UserExperienceAlreadyPresent(String message){
        super((message));
    }
}
