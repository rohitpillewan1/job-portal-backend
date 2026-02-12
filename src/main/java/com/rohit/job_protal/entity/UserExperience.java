package com.rohit.job_protal.entity;
import com.rohit.job_protal.enums.EmploymentStatus;
import com.rohit.job_protal.enums.EmploymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
public class UserExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private UserProfile userProfile;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private String role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;
    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(length = 1000)
    private String description;
}
