package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserEducationDto;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserEducation;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.enums.EducationStatus;
import com.rohit.job_protal.exception.EducationAlreadyPresent;
import com.rohit.job_protal.exception.EndDateNotePresent;
import com.rohit.job_protal.exception.UserProfileNotFound;
import com.rohit.job_protal.repository.UserEducationRepository;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserEducationServiceImp implements UserEducationService{

    @Autowired
    private UserEducationRepository userEducationRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SecurityUtil securityUtil;
    @Override
    public UserEducation saveUserEducation(UserEducationDto userEducationDto) {
        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findByUser(user);
        if(userProfile==null){
            throw  new UserProfileNotFound("User profile not found first create user profile");
        }
        if(userEducationRepository.existsByUserProfileAndDegreeAndInstituteAndStartDate(userProfile,userEducationDto.getDegree(),userEducationDto.getInstitute(),userEducationDto.getStartDate())){
            throw new EducationAlreadyPresent("Education Already present Add education with another degree ");
        }
        if(userEducationDto.getStartDate().isAfter(userEducationDto.getEndData())){
            throw new EndDateNotePresent("End date cannot be before start date");
        }
        UserEducation userEducation = new UserEducation();
        userEducation.setUserProfile(userProfile);
        userEducation.setDegree(userEducationDto.getDegree());
        userEducation.setInstitute(userEducationDto.getInstitute());
        userEducation.setPercentage(userEducationDto.getPercentage());
        userEducation.setStartDate(userEducationDto.getStartDate());
        userEducation.setEndData(userEducationDto.getEndData());
        userEducation.setCreatedAt(LocalDateTime.now());
        userEducation.setUpdatedAt(LocalDateTime.now());
        return userEducationRepository.save(userEducation);
    }
}
