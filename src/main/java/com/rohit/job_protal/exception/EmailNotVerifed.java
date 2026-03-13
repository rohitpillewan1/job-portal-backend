package com.rohit.job_protal.exception;

import com.rohit.job_protal.service.EmailService;

public class EmailNotVerifed extends RuntimeException{
    public EmailNotVerifed(String message){
        super(message);
    }
}
