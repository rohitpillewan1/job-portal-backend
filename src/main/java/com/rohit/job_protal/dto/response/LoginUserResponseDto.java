package com.rohit.job_protal.dto.response;

import com.rohit.job_protal.enums.Role;
import lombok.Data;

@Data
public class LoginUserResponseDto {
	private Long id;
	private String name;
	private String email;
	private Role role;
	private String jwtToken;
	private String type="Bearer";
}
