package com.rohit.job_protal.dto.request;

import com.rohit.job_protal.enums.Gender;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class UserProfileDto {
    private Gender gender;

    @NotBlank(message = "Phone number is mandatory")
    // Use @Size for character length check
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    // Optional: Use @Pattern to ensure only numbers are entered
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain only digits")
    private String phone;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "State is mandatory")
    private String state;

    @NotBlank(message = "Country is mandatory")
    private String country;

    @NotNull(message = "Total experience is required")
    @Min(value = 0, message = "Total experience must be 0 or greater")
    private Integer totalExperience;
}