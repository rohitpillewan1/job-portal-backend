package com.rohit.job_protal.dto.request;

import com.rohit.job_protal.enums.ApplicationStatus;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class ApplicationStatusDto {
    private ApplicationStatus applicationStatus;
}
