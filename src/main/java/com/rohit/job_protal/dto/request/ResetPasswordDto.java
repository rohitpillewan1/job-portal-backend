package com.rohit.job_protal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordDto {
    @NotBlank(message = "Token should be present")
    private String token;
    @NotBlank(message = "password should not be empty")
    @Size(min = 6,max = 20,message = "Password must be between 6 and 20 characters")
    private String password;
}
