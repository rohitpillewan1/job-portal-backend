package com.rohit.job_protal.repository;

import com.rohit.job_protal.entity.UserExperience;
import com.rohit.job_protal.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserExperienceRepository extends JpaRepository<UserExperience,Long> {
    public boolean existsUserExperienceByCompanyNameAndStartDateAndRoleAndUserProfile(String companyName, LocalDate  startDate, String role, UserProfile userProfile);
}
