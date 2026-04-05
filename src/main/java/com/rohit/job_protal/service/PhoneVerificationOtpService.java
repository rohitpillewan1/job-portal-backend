package com.rohit.job_protal.service;

import com.rohit.job_protal.entity.PhoneVerificationOtp;

public interface PhoneVerificationOtpService {
    public void generateOtp();
    public void verifyOtp(String otp);
    public void sendOtp(PhoneVerificationOtp phoneVerificationOtp,String otp);
}
