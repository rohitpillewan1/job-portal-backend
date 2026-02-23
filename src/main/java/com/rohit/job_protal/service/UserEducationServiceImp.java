package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserEducationDto;
import com.rohit.job_protal.dto.response.UserEducationResponseDto;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserEducation;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.exception.*;
import com.rohit.job_protal.repository.UserEducationRepository;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.security.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserEducationServiceImp implements UserEducationService {

    @Autowired
    private UserEducationRepository userEducationRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SecurityUtil securityUtil;

    // ================= SAVE =================

    @Override
    public UserEducationResponseDto saveUserEducation(UserEducationDto dto) {

        UserProfile userProfile = getCurrentUserProfile();
        validateDates(dto);

        if (userEducationRepository
                .existsByUserProfileAndDegreeAndInstituteAndStartDate(
                        userProfile,
                        dto.getDegree(),
                        dto.getInstitute(),
                        dto.getStartDate())) {

            throw new EducationAlreadyPresent(
                    "Education already present. Add education with another degree");
        }

        UserEducation userEducation = new UserEducation();
        userEducation.setUserProfile(userProfile);
        userEducation.setEducationStatus(dto.getEducationStatus());
        userEducation.setDegree(dto.getDegree());
        userEducation.setInstitute(dto.getInstitute());
        userEducation.setPercentage(dto.getPercentage());
        userEducation.setStartDate(dto.getStartDate());
        userEducation.setEndData(dto.getEndDate());
        userEducation.setCreatedAt(LocalDateTime.now());
        userEducation.setUpdatedAt(LocalDateTime.now());

        UserEducation savedEducation = userEducationRepository.save(userEducation);

        return mapToResponse(savedEducation);
    }

    // ================= GET ALL =================

    @Override
    public List<UserEducationResponseDto> getAllUserEducation() {

        UserProfile userProfile = getCurrentUserProfile();

        List<UserEducation> educations =
                userEducationRepository.findAllByUserProfile(userProfile);

        List<UserEducationResponseDto> responseList = new ArrayList<>();

        for (UserEducation education : educations) {
            responseList.add(mapToResponse(education));
        }

        return responseList;
    }

    // ================= UPDATE =================

    @Override
    public UserEducationResponseDto updateUserEducation(Long id, UserEducationDto dto) {

        UserProfile userProfile = getCurrentUserProfile();
        validateDates(dto);

        if (userEducationRepository
                .existsByUserProfileAndDegreeAndInstituteAndStartDate(
                        userProfile,
                        dto.getDegree(),
                        dto.getInstitute(),
                        dto.getStartDate())) {

            throw new EducationAlreadyPresent(
                    "Education already present. Add education with another degree");
        }

        UserEducation userEducation =
                userEducationRepository.findUserEducationByUserProfileAndId(userProfile, id);

        if (userEducation == null) {
            throw new NotFoundException("Education not found");
        }

        userEducation.setEducationStatus(dto.getEducationStatus());
        userEducation.setInstitute(dto.getInstitute());
        userEducation.setDegree(dto.getDegree());
        userEducation.setStartDate(dto.getStartDate());
        userEducation.setEndData(dto.getEndDate());
        userEducation.setPercentage(dto.getPercentage());
        userEducation.setUpdatedAt(LocalDateTime.now());

        userEducationRepository.save(userEducation);

        return mapToResponse(userEducation);
    }

    @Transactional
    @Override
    public void deleteUserEducation(long id) {
        UserProfile userProfile = getCurrentUserProfile();
        if(!userEducationRepository.existsUserEducationByUserProfileAndId(userProfile,id)){
            throw new NotFoundException("User education not found");
        }
        userEducationRepository.deleteUserEducationByUserProfileAndId(userProfile,id);
    }


    // ================= HELPER METHODS =================

    private UserProfile getCurrentUserProfile() {

        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findByUser(user);

        if (userProfile == null) {
            throw new UserProfileNotFound(
                    "User profile not found. First create user profile.");
        }

        return userProfile;
    }

    private void validateDates(UserEducationDto dto) {

        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new EndDateNotePresent(
                    "End date cannot be before start date");
        }
    }

    private UserEducationResponseDto mapToResponse(UserEducation education) {

        UserEducationResponseDto response = new UserEducationResponseDto();
        response.setId(education.getId());
        response.setDegree(education.getDegree());
        response.setEducationStatus(education.getEducationStatus());
        response.setInstitute(education.getInstitute());
        response.setStartDate(education.getStartDate());
        response.setEndDate(education.getEndData());
        response.setPercentage(education.getPercentage());

        return response;
    }
}
