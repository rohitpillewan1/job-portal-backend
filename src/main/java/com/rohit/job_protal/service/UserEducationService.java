package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserEducationDto;
import com.rohit.job_protal.entity.UserEducation;

public interface UserEducationService {
    public UserEducation saveUserEducation(UserEducationDto userEducationDto);
}
