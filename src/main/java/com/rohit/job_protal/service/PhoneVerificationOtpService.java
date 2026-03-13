package com.rohit.job_protal.service;

public interface PhoneVerificationOtpService {
    public void generateOtp();
    public void verifyOtp(String otp);
}
