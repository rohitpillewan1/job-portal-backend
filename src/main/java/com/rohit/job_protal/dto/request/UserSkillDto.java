package com.rohit.job_protal.dto.request;

import com.rohit.job_protal.enums.Proficiency;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSkillDto {

    @NotNull(message = "Id should not be empty")
    private long skillId;

    @NotNull(message = "experience should not be blank")
    @Min(0)
    private Integer experience;   // years

    @NotNull(message = "proficiency should not be blank")
    @Enumerated(EnumType.STRING)
    private Proficiency proficiency;
}
