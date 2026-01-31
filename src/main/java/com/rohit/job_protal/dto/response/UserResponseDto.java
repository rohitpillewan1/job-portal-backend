package com.rohit.job_protal.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {
	private Long id;
	private String name;
	private String jwtToken;
}
