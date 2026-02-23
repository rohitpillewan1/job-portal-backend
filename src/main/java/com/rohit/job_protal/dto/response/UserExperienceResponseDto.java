package com.rohit.job_protal.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rohit.job_protal.enums.EmploymentStatus;
import com.rohit.job_protal.enums.EmploymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@JsonPropertyOrder({
        "id",
        "companyName",
        "role",
        "employmentType",
        "employmentStatus",
        "description",
        "startDate",
        "endDate",

})
@Data
public class UserExperienceResponseDto {
    private Long id;
    private String companyName;
    private String role;
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
