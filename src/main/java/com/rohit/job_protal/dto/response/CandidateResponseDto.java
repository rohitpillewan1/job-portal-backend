package com.rohit.job_protal.dto.response;

import com.rohit.job_protal.dto.request.UserResumeDto;
import com.rohit.job_protal.dto.request.UserSkillDto;
import com.rohit.job_protal.enums.ApplicationStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CandidateResponseDto {

    private Long userId;
    private Long applicationId;
    private String name;
    private String email;

    private UserProfileResponseDto profile;

    private List<UserSkillDtoReponse> skills;

    private List<UserEducationResponseDto> education;

    private List<UserExperienceResponseDto> experience;

    private UserResumeDto resume;

    private LocalDateTime appliedAt;

    private ApplicationStatus applicationStatus;
}
