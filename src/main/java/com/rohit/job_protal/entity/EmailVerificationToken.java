package com.rohit.job_protal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class EmailVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private LocalDateTime expiryDate;

    private boolean used = false;
}
