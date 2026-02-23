package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserEducationDto;
import com.rohit.job_protal.dto.response.UserEducationResponseDto;

import java.util.List;

public interface UserEducationService {
    public UserEducationResponseDto saveUserEducation(UserEducationDto userEducationDto);
    public List<UserEducationResponseDto> getAllUserEducation();
    public UserEducationResponseDto updateUserEducation(Long id,UserEducationDto userEducationDto);
    public void deleteUserEducation(long id);
}
