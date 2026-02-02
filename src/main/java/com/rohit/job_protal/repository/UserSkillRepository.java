package com.rohit.job_protal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.entity.UserSkill;
import com.rohit.job_protal.entity.UserSkillId;

public interface UserSkillRepository extends JpaRepository<UserSkill, UserSkillId> {

    List<UserSkill> findByUserProfile(UserProfile userProfile);

    boolean existsByUserProfile(UserProfile userProfile);

    void deleteByUserProfile(UserProfile userProfile);
}
