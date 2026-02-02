package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserProfileDto;
import com.rohit.job_protal.entity.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface UserProfileService {
    public UserProfile saveUserProfile(UserProfileDto userProfile);
}
