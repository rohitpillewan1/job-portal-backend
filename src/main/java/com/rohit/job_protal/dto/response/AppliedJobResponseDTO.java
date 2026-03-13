package com.rohit.job_protal.dto.response;

import com.rohit.job_protal.enums.ApplicationStatus;
import com.rohit.job_protal.enums.EmploymentType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppliedJobResponseDTO {

    private Long applicationId;

    private Long jobId;
    private String jobTitle;

    private String companyName;
    private String companyLogo;

    private String location;
    private EmploymentType employmentType;

    private Integer salaryMin;
    private Integer salaryMax;

    private ApplicationStatus applicationStatus;

    private LocalDateTime appliedAt;
}

