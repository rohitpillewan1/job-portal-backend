package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserExperienceDto;
import com.rohit.job_protal.dto.response.UserExperienceResponseDto;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserExperience;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.enums.EmploymentStatus;
import com.rohit.job_protal.exception.EndDateNotePresent;
import com.rohit.job_protal.exception.NotFoundException;
import com.rohit.job_protal.exception.UserExperienceAlreadyPresent;
import com.rohit.job_protal.exception.UserProfileNotFound;
import com.rohit.job_protal.repository.UserExperienceRepository;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.security.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserExperienceServiceImp implements UserExperienceService{
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserExperienceRepository userExperienceRepository;
    @Override
    public UserExperienceResponseDto saveUserExperience(UserExperienceDto userExperienceDto) {

        UserProfile userProfile = getCurrentUserProfile();

        if(userExperienceRepository.existsUserExperienceByCompanyNameAndStartDateAndRoleAndUserProfile(userExperienceDto.getCompanyName(),userExperienceDto.getStartDate(),userExperienceDto.getRole(),userProfile)){
            throw  new UserExperienceAlreadyPresent("This experience is already present add another experience");
        }
        UserExperience userExperience = new UserExperience();

        if (userExperienceDto.getEmploymentStatus() == EmploymentStatus.CURRENT) {
            userExperience.setEndDate(null);
        } else {
            if (userExperienceDto.getEndDate() == null) {
                throw new EndDateNotePresent("End date required for past employment");
            }
            if (userExperienceDto.getEndDate().isBefore(userExperienceDto.getStartDate())) {
                throw new EndDateNotePresent("End date cannot be before start date");
            }
            userExperience.setEndDate(userExperienceDto.getEndDate());
        }

        userExperience.setUserProfile(userProfile);
        userExperience.setDescription(userExperienceDto.getDescription());
        userExperience.setCompanyName(userExperienceDto.getCompanyName());
        userExperience.setStartDate(userExperienceDto.getStartDate());
        userExperience.setRole(userExperienceDto.getRole());
        userExperience.setEmploymentStatus(userExperienceDto.getEmploymentStatus());
        userExperience.setEmploymentType(userExperienceDto.getEmploymentType());

       UserExperience userExperienceRes =  userExperienceRepository.save(userExperience);
       return mapToResponse(userExperienceRes);

    }
    public List<UserExperienceResponseDto> getAllUserExperience(){
        UserProfile userProfile = getCurrentUserProfile();
        List<UserExperience> userExperiences = userExperienceRepository.findAllByUserProfile(userProfile);
        List<UserExperienceResponseDto> responseDtos = new ArrayList<>();
        for(UserExperience userExperience : userExperiences){
            responseDtos.add(mapToResponse(userExperience));
        }
        return responseDtos;

     }

     public UserExperienceResponseDto updateUserExperience(Long id,UserExperienceDto userExperienceDto){
        UserProfile userProfile = getCurrentUserProfile();
        System.out.println(userExperienceDto.toString());
        UserExperience userExperience = userExperienceRepository.findUserExperienceByUserProfileAndId(userProfile,id);
        if(userExperience==null){
            throw  new NotFoundException("Experience Not Found");
        }
         if(userExperienceRepository.existsUserExperienceByCompanyNameAndStartDateAndRoleAndUserProfileAndIdNot(userExperienceDto.getCompanyName(),userExperienceDto.getStartDate(),userExperienceDto.getRole(),userProfile,id)){
             throw  new UserExperienceAlreadyPresent("This experience is already present add another experience");
         }
         if (userExperienceDto.getEmploymentStatus() == EmploymentStatus.CURRENT) {
             userExperience.setEndDate(null);
         } else {
             if (userExperienceDto.getEndDate() == null) {
                 throw new EndDateNotePresent("End date required for past employment");
             }
             if (userExperienceDto.getEndDate().isBefore(userExperienceDto.getStartDate())) {
                 throw new EndDateNotePresent("End date cannot be before start date");
             }
             userExperience.setEndDate(userExperienceDto.getEndDate());
         }
         userExperience.setDescription(userExperienceDto.getDescription());
         userExperience.setCompanyName(userExperienceDto.getCompanyName());
         userExperience.setStartDate(userExperienceDto.getStartDate());
         userExperience.setRole(userExperienceDto.getRole());
         userExperience.setEmploymentStatus(userExperienceDto.getEmploymentStatus());
         userExperience.setEmploymentType(userExperienceDto.getEmploymentType());
         UserExperience userExperience1 = userExperienceRepository.save(userExperience);
         return mapToResponse(userExperience1);
     }

     @Transactional
     public void deleteUserExperience(Long id){
        UserProfile userProfile = getCurrentUserProfile();
        System.out.println(id);
        if(!userExperienceRepository.existsUserExperienceByUserProfileAndId(userProfile,id)){
            throw  new NotFoundException("User Experience Not Found");
        }
        userExperienceRepository.deleteByIdAndUserProfile(id,userProfile);
     }

     public UserExperienceResponseDto mapToResponse(UserExperience userExperience){
        UserExperienceResponseDto response  = new UserExperienceResponseDto();
        response.setId(userExperience.getId());
        response.setDescription(userExperience.getDescription());
        response.setRole(userExperience.getRole());
        response.setEndDate(userExperience.getEndDate());
        response.setCompanyName(userExperience.getCompanyName());
        response.setEmploymentStatus(userExperience.getEmploymentStatus());
        response.setEmploymentType(userExperience.getEmploymentType());
        response.setStartDate(userExperience.getStartDate());

        return response;
     }

    public UserProfile getCurrentUserProfile(){
        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findUserProfileByUser(user);
        if(userProfile==null){
            throw new UserProfileNotFound("User profile not found");
        }
        return userProfile;
    }
}
