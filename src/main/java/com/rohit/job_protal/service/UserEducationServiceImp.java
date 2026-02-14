package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserEducationDto;
import com.rohit.job_protal.dto.response.UserEducationResponseDto;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserEducation;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.exception.EducationAlreadyPresent;
import com.rohit.job_protal.exception.EndDateNotePresent;
import com.rohit.job_protal.exception.UserProfileNotFound;
import com.rohit.job_protal.repository.UserEducationRepository;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserEducationServiceImp implements UserEducationService{

    @Autowired
    private UserEducationRepository userEducationRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SecurityUtil securityUtil;
    @Override
    public UserEducationResponseDto saveUserEducation(UserEducationDto userEducationDto) {
        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findByUser(user);
        if(userProfile==null){
            throw  new UserProfileNotFound("User profile not found first create user profile");
        }
        if(userEducationRepository.existsByUserProfileAndDegreeAndInstituteAndStartDate(userProfile,userEducationDto.getDegree(),userEducationDto.getInstitute(),userEducationDto.getStartDate())){
            throw new EducationAlreadyPresent("Education Already present Add education with another degree ");
        }
        if(userEducationDto.getStartDate().isAfter(userEducationDto.getEndDate())){
            throw new EndDateNotePresent("End date cannot be before start date");
        }
        UserEducation userEducation = new UserEducation();
        userEducation.setUserProfile(userProfile);
        userEducation.setDegree(userEducationDto.getDegree());
        userEducation.setInstitute(userEducationDto.getInstitute());
        userEducation.setPercentage(userEducationDto.getPercentage());
        userEducation.setStartDate(userEducationDto.getStartDate());
        userEducation.setEndData(userEducationDto.getEndDate());
        userEducation.setCreatedAt(LocalDateTime.now());
        userEducation.setUpdatedAt(LocalDateTime.now());
       UserEducation userEducation1 =  userEducationRepository.save(userEducation);

        UserEducationResponseDto userEducationRes = new UserEducationResponseDto();
        userEducationRes.setId(userEducation1.getId());
        userEducationRes.setDegree(userEducation1.getDegree());
        userEducationRes.setEducationStatus(userEducation1.getEducationStatus());
        userEducationRes.setInstitute(userEducation1.getInstitute());
        userEducationRes.setStartDate(userEducation1.getStartDate());
        userEducationRes.setEndDate(userEducation1.getEndData());
        userEducationRes.setPercentage(userEducation1.getPercentage());

        return userEducationRes;
    }

    @Override
    public List<UserEducationResponseDto> getAllUserEducation() {

        User user = securityUtil.getCurrentUser();
        UserProfile userProfile= userProfileRepository.findByUser(user);
        List<UserEducation> userEducations  = userEducationRepository.findAllByUserProfile(userProfile);
        List<UserEducationResponseDto> userEducationResponseDtos = new ArrayList<>();
        for(UserEducation userEducation : userEducations ){
            UserEducationResponseDto userEducationRes = new UserEducationResponseDto();
            userEducationRes.setId(userEducation.getId());
            userEducationRes.setDegree(userEducation.getDegree());
            userEducationRes.setEducationStatus(userEducation.getEducationStatus());
            userEducationRes.setInstitute(userEducation.getInstitute());
            userEducationRes.setStartDate(userEducation.getStartDate());
            userEducationRes.setEndDate(userEducation.getEndData());
            userEducationRes.setPercentage(userEducation.getPercentage());
            userEducationResponseDtos.add(userEducationRes);
        }
        return  userEducationResponseDtos;
    }
}
