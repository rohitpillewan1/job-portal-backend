package com.rohit.job_protal.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SkillResponseDto {
	private long id;
	@NotBlank(message = "Skill is required")
    private String name;
}
