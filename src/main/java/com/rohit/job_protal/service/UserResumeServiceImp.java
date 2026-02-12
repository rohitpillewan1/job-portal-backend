package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserResumeDto;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.entity.UserResume;
import com.rohit.job_protal.exception.UserProfileNotFound;
import com.rohit.job_protal.exception.UserResumeAlreadyPresent;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.repository.UserResumeRepository;
import com.rohit.job_protal.security.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserResumeServiceImp implements  UserResumeService{

    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserResumeRepository userResumeRepository;

    @Override
    public void saveUserResume(UserResumeDto userResumeDto) {
        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findByUser(user);
        if(userProfile==null){
            throw  new UserProfileNotFound("User profile not found first create user profile");
        }
        if(userResumeRepository.existsUserResumeByFileNameAndUserProfile(userResumeDto.getFileName(),userProfile)){
            throw  new UserResumeAlreadyPresent(userResumeDto.getFileName()+" file already present upload another resume");
        }
        if(userResumeRepository.existsByUserProfileAndActiveTrue(userProfile)){
            userResumeRepository.deactivateActive(userProfile.getId());
        }

        UserResume userResume = new UserResume();
        userResume.setUserProfile(userProfile);
        userResume.setActive(true);
        userResume.setFileName(userResumeDto.getFileName());
        userResume.setFileUrl(userResumeDto.getFileUrl());
        userResume.setUploadAt(LocalDateTime.now());
        userResumeRepository.save(userResume);
    }
}
