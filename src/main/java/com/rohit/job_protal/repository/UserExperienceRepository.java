package com.rohit.job_protal.repository;

import com.rohit.job_protal.entity.UserExperience;
import com.rohit.job_protal.entity.UserProfile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserExperienceRepository extends JpaRepository<UserExperience,Long> {
    public boolean existsUserExperienceByCompanyNameAndStartDateAndRoleAndUserProfile(String companyName, LocalDate  startDate, String role, UserProfile userProfile);

    UserExperience findUserExperienceByUserProfile(UserProfile userProfile);

    Boolean existsUserExperienceByUserProfile(UserProfile userProfile);

    List<UserExperience> findAllByUserProfile(UserProfile userProfile);

    UserExperience findUserExperienceByUserProfileAndId(UserProfile userProfile, Long id);

    boolean existsUserExperienceByUserProfileAndId(UserProfile userProfile, Long id);

    void deleteByIdAndUserProfile(Long id,UserProfile userProfile);

    boolean existsUserExperienceByCompanyNameAndStartDateAndRoleAndUserProfileAndIdNot(String companyName, LocalDate startDate, String role, UserProfile userProfile,Long id);

    List<UserExperience> id(Long id);
}
