package com.rohit.job_protal.entity;

import com.rohit.job_protal.enums.EmploymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JobPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="profile_id")
    private UserProfile userProfile;
    private String preferredLocation;
    private EmploymentType jobType;
    private Double expectedSalary;
}
