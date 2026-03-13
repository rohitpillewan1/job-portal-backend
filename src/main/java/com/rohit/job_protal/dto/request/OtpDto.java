package com.rohit.job_protal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OtpDto {
    @NotBlank
    private String otp;
}
