package com.rohit.job_protal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private UserProfile userProfile;
    private String degree;
    private Double percentage;
    private String institute;
    private String startDate;
    private String endData;
}
