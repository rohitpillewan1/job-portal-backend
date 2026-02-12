package com.rohit.job_protal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResumeDto {
    @NotBlank(message = "File name is required")
    private String fileName;
    @NotBlank(message = "File url is required")
    private String fileUrl;
}
