package com.rohit.job_protal.repository;

import com.rohit.job_protal.entity.PhoneVerificationOtp;
import com.rohit.job_protal.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneVerificationOtpRepository extends JpaRepository<PhoneVerificationOtp,Long> {
    public Optional<PhoneVerificationOtp> findByUserProfile(UserProfile userProfile);
    public void deleteByUserProfile(UserProfile userProfile);
}
