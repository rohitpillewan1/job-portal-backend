package com.rohit.job_protal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
	@NotBlank(message = "Email should not be empty")
	@Email
	private String email;
	@NotBlank(message = "password should not be empty")
	private String password;
}
