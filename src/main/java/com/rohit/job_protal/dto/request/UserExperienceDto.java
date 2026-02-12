package com.rohit.job_protal.dto.request;

import com.rohit.job_protal.enums.EmploymentStatus;
import com.rohit.job_protal.enums.EmploymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserExperienceDto {
    @NotBlank(message = "Company name should not be empty")
    private String companyName;

    @NotBlank(message = "Role should not be empty")
    private String role;

    // Use @NotNull for Enums
    @NotNull(message = "Employment type should not be empty")
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    // Use @NotNull for Enums
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Employment status should not be empty")
    private EmploymentStatus employmentStatus;

    // Use @NotNull for Dates
    @NotNull(message = "Start date should not be empty")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotBlank(message = "Description should not be empty")
    @Size(min = 100, message = "Description must be at least 100 characters")
    private String description;
}
