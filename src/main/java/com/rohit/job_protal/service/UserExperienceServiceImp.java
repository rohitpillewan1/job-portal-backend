package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserExperienceDto;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserExperience;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.enums.EmploymentStatus;
import com.rohit.job_protal.exception.EndDateNotePresent;
import com.rohit.job_protal.exception.UserExperienceAlreadyPresent;
import com.rohit.job_protal.exception.UserProfileNotFound;
import com.rohit.job_protal.repository.UserExperienceRepository;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserExperienceServiceImp implements UserExperienceService{
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserExperienceRepository userExperienceRepository;
    @Override
    public void saveUserExperience(UserExperienceDto userExperienceDto) {
        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findByUser(user);
        if(userProfile==null){
            throw new UserProfileNotFound("User profile not found first create user profile");
        }
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

        userExperienceRepository.save(userExperience);

    }
}
