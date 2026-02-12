package com.rohit.job_protal.repository;

import com.rohit.job_protal.dto.request.UserResumeDto;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.entity.UserResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserResumeRepository extends JpaRepository<UserResume, Long> {

    boolean existsByUserProfileAndActiveTrue(UserProfile userProfile);

    @Modifying
    @Query("""
        UPDATE UserResume r 
        SET r.active = false 
        WHERE r.userProfile.id = :profileId 
          AND r.active = true
    """)
    void deactivateActive(@Param("profileId") Long profileId);

    Boolean existsUserResumeByFileNameAndUserProfile(String fileName, UserProfile userProfile);
}

