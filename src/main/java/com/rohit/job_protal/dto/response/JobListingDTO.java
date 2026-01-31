package com.rohit.job_protal.dto.response;

import java.util.List;

import com.rohit.job_protal.enums.EmploymentType;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "id",
    "title",
    "location",
    "employmentType",
    "salaryMin",
    "salaryMax",
    "company",
    "skills"
})
@Data
public class JobListingDTO {

    private Long id;
    private String title;
    private String location;
    private EmploymentType employmentType;
    private Integer salaryMin;
    private Integer salaryMax;
    private CompanyDTO company;
    private List<SkillResponseDto> skills;
}
