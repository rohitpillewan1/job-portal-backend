package com.rohit.job_protal.service;

public interface EmailVerificationTokenService {
    public void verifyEmail(String token);
}
