package com.rohit.job_protal.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rohit.job_protal.enums.EmploymentType;
import com.rohit.job_protal.enums.JobStatus;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "id",
    "title",
    "description",
    "location",
    "employmentType",
    "experienceRequired",
    "salaryMin",
    "salaryMax",
    "status",
    "company",
    "skills"
})
@Data
public class JobDetailsDTO {

    private Long id;
    private String title;
    private String description;
    private String location;
    private EmploymentType employmentType;
    private Integer experienceRequired;
    private Integer salaryMin;
    private Integer salaryMax;
    private JobStatus status;
    private CompanyDetailsDTO company;
    private List<SkillResponseDto> skills;

    // getters & setters
}
