package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserProfileDto;
import com.rohit.job_protal.dto.response.UserProfileResponseDto;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.exception.NotFoundException;
import com.rohit.job_protal.exception.UserAlreadyExist;
import com.rohit.job_protal.exception.UserExperienceAlreadyPresent;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserProfileServiceImp implements UserProfileService {

    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ProfileStatusService profileStatusService;

    @Override
    public UserProfileResponseDto saveUserProfile(UserProfileDto dto) {

        if(userProfileRepository.existsByPhone(dto.getPhone())){
            throw new UserAlreadyExist("Phone number already register");
        }

        User currentUser = getCurrentUser();

        if (userProfileRepository.existsByUser(currentUser)) {
            throw new UserExperienceAlreadyPresent("User profile already exists");
        }

        UserProfile profile = new UserProfile();
        profile.setUser(currentUser);
        profile.setGender(dto.getGender());
        profile.setPhone(dto.getPhone());
        profile.setPhoneVerified(false);
        profile.setCity(dto.getCity());
        profile.setState(dto.getState());
        profile.setCountry(dto.getCountry());
        profile.setTotalExperience(dto.getTotalExperience());
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

       UserProfile userProfile = userProfileRepository.save(profile);
       return mapToReponse(userProfile);
    }

    @Override
    public UserProfileResponseDto getUserProfile() {
        User currentUser = getCurrentUser();
        if(!userProfileRepository.existsByUser(currentUser)){
            throw new NotFoundException("User Profile Not Found first create user profile");
        }
        UserProfile userProfile = userProfileRepository.findUserProfileByUser(currentUser);
        return mapToReponse(userProfile);
    }



    public User getCurrentUser(){
        return securityUtil.getCurrentUser();
    }

    public UserProfileResponseDto mapToReponse(UserProfile userProfile){
        UserProfileResponseDto response = new UserProfileResponseDto();
        response.setId(userProfile.getId());
        response.setGender(userProfile.getGender());
        response.setPhone(userProfile.getPhone());
        response.setCity(userProfile.getCity());
        response.setPhoneVerified(userProfile.isPhoneVerified());
        response.setState(userProfile.getState());
        response.setCountry(userProfile.getCountry());
        response.setTotalExperience(userProfile.getTotalExperience());
        response.setProfileStausPercentage(profileStatusService.getProfileStatusPercenatage());

        return response;
    }
}

