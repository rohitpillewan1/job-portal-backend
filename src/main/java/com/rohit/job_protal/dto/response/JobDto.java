package com.rohit.job_protal.dto.response;
import java.util.ArrayList;
import java.util.List;
import com.rohit.job_protal.enums.EmploymentType;
import com.rohit.job_protal.enums.JobStatus;
import lombok.Data;
import jakarta.validation.constraints.*;
@Data
public class JobDto {

    @NotNull(message = "Company ID is required")
    private Long company_id;

    @NotEmpty(message = "At least one skill must be provided")
    private List<Long> skill = new ArrayList<>();

    @NotBlank(message = "Job title cannot be empty")
    @Size(min = 3, max = 100, message = "Title should be between 3 to 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    // ENUM VALIDATION
    @NotNull(message = "Employment type (Full-time, Part-time, etc.) is required")
    private EmploymentType employmentType;

    @Min(value = 0, message = "Experience cannot be negative")
    private Integer experienceRequired;

    @Positive(message = "Minimum salary must be greater than zero")
    private Integer salaryMin;

    @Positive(message = "Maximum salary must be greater than zero")
    private Integer salaryMax;

    // ENUM VALIDATION
    @NotNull(message = "Job status is required")
    private JobStatus jobStatus;
}
