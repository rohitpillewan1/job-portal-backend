package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserExperienceDto;
import com.rohit.job_protal.dto.response.UserExperienceResponseDto;

import java.util.List;

public interface UserExperienceService {
    public UserExperienceResponseDto saveUserExperience(UserExperienceDto userExperienceDto);
    public List<UserExperienceResponseDto> getAllUserExperience();
    UserExperienceResponseDto updateUserExperience(Long id,UserExperienceDto userExperienceDto);
    void deleteUserExperience(Long id);
}
