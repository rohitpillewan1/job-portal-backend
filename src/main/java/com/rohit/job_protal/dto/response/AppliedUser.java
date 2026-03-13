package com.rohit.job_protal.dto.response;

import com.rohit.job_protal.enums.ApplicationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppliedUser {
    private Long applicationId;
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String city;
    private Integer totalExperience;
    private LocalDateTime appliedAt;
    private ApplicationStatus applicationStatus;
}
