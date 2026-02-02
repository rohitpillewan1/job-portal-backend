package com.rohit.job_protal.dto.request;

import com.rohit.job_protal.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileDto {
    private Gender gender;
    @NotBlank(message = "Phone number is mandatory")
    @Min(10)
    private String phone;
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotBlank(message = "State is mandatory")
    private String state;
    @NotBlank(message = "Country is mandatory")
    private String country;
    @NotBlank(message = "Total experience is mandatory")
    @Min(value = 0, message = "Experience cannot be negative")
    private Integer totalExperience;
}
