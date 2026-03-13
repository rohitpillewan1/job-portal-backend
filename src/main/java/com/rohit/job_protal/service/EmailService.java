package com.rohit.job_protal.service;

import com.rohit.job_protal.entity.User;

public interface EmailService {
    public void sendEmailVerification(User user, String token);
    public void sendEmailPasswordReset(User user, String token);
    public void sendApplicactionStatusChange(Long applicationId);
}
