package com.rohit.job_protal.repository;

import com.rohit.job_protal.entity.UserEducation;
import com.rohit.job_protal.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface UserEducationRepository extends JpaRepository<UserEducation,Long> {
    boolean existsByUserProfileAndDegreeAndInstituteAndStartDate(
            UserProfile userProfile,
            String degree,
            String institute,
            LocalDate startDate
    );

    Boolean existsUserEducationByUserProfile(UserProfile userProfile);

    List<UserEducation> findAllByUserProfile(UserProfile userProfile);
}
