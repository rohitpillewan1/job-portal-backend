package com.rohit.job_protal.dto.response;

import lombok.Data;

@Data
public class SignupResponseDto {
    private Long id;
    private String name;
    private String email;
    private String message;
}
