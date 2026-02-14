package com.rohit.job_protal.dto.request;

import com.rohit.job_protal.enums.EducationStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEducationDto {

    @NotBlank(message = "Degree should not be empty")
    @Size(min = 2, max = 100, message = "Degree must be between 2 and 100 characters")
    private String degree;

    @NotNull(message = "Percentage is required")
    @DecimalMin(value = "0.0", message = "Percentage must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Percentage must be less than or equal to 100")
    private Double percentage;

    @NotBlank(message = "Institute name should not be empty")
    @Size(min = 2, max = 150, message = "Institute name must be between 2 and 150 characters")
    private String institute;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Education status is required")
    private EducationStatus educationStatus;
}
