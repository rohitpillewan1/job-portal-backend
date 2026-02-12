package com.rohit.job_protal.dto.request;

import com.rohit.job_protal.enums.EducationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEducationDto {
    @NotBlank(message = "degree should not be empty")
    private String degree;
    private Double percentage;
    private String institute;
    private LocalDate startDate;
    private LocalDate endData;
    @Enumerated(EnumType.STRING)
    private EducationStatus educationStatus;
}
