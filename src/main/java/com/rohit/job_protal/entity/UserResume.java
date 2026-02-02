package com.rohit.job_protal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UserResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private UserProfile userProfile;
    private String fileName;
    private String fileUrl;
    private Boolean active;
    private LocalDateTime uploadAt;
}
