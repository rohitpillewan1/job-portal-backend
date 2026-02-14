package com.rohit.job_protal.exception;

public class JobAlreadyApplied extends RuntimeException{
    public JobAlreadyApplied(String message){
        super(message);
    }
}
