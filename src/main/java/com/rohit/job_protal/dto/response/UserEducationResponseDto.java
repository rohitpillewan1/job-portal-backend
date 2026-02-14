package com.rohit.job_protal.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rohit.job_protal.enums.EducationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonPropertyOrder({
        "id",
        "degree",
        "percentage",
        "institute",
        "startDate",
        "endDate",
        "educationStatus"
})
public class UserEducationResponseDto {
    private Long id;
    private String degree;
    private Double percentage;
    private String institute;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private EducationStatus educationStatus;
}
