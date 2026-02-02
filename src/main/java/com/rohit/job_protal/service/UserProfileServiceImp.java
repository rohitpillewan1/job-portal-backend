package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserProfileDto;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.enums.ProfileStatus;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class UserProfileServiceImp implements UserProfileService {

    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfile saveUserProfile(UserProfileDto dto) {

        User currentUser = securityUtil.getCurrentUser();

        if (userProfileRepository.existsByUser(currentUser)) {
            throw new RuntimeException("User profile already exists");
        }

        UserProfile profile = new UserProfile();
        profile.setUser(currentUser);
        profile.setGender(dto.getGender());
        profile.setPhone(dto.getPhone());
        profile.setCity(dto.getCity());
        profile.setState(dto.getState());
        profile.setCountry(dto.getCountry());
        profile.setTotalExperience(dto.getTotalExperience());

        profile.setProfileStatus(ProfileStatus.INCOMPLETE);
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        return userProfileRepository.save(profile);
    }
}

