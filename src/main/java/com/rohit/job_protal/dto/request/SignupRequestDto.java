package com.rohit.job_protal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDto {

	@NotBlank(message = "Name should not be empty")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	private String name;

	@NotBlank(message = "Email should not be empty")
	@Email(message = "Invalid email format")
	@Pattern(
			regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
			message = "Email must contain valid domain (e.g., example@gmail.com)"
	)
	private String email;

	@NotBlank(message = "Password should not be empty")
	@Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
	@Pattern(
			regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,20}$",
			message = "Password must contain at least one uppercase letter, one number, and one special character"
	)
	private String password;
}
