package com.rohit.job_protal.entity;

import io.lettuce.core.dynamic.annotation.CommandNaming;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class PhoneVerificationOtp {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String otp;
    private boolean isUsed;
    private LocalDateTime expiryDate;
    @OneToOne
    @JoinColumn(name = "user_profile_id",nullable = false,unique = true)
    private UserProfile userProfile;
}
