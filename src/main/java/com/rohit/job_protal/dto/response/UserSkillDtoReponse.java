package com.rohit.job_protal.dto.response;

import com.rohit.job_protal.entity.Skill;
import com.rohit.job_protal.enums.Proficiency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSkillDtoReponse {

    @NotNull(message = "Id should not be empty")
    private Long id;
    private String name;

    @NotNull(message = "experience should not be blank")
    @Min(0)
    private Integer experience;   // years

    @NotNull(message = "proficiency should not be blank")
    @Enumerated(EnumType.STRING)
    private Proficiency proficiency;
}
