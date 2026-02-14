package com.rohit.job_protal.repository;
import java.util.List;
import java.util.Optional;

import com.rohit.job_protal.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.entity.UserSkill;
import com.rohit.job_protal.entity.UserSkillId;

public interface UserSkillRepository extends JpaRepository<UserSkill, UserSkillId> {

    Optional<UserSkill> findByUserProfileAndSkill(UserProfile userProfile, Skill skill);

    Boolean existsUserSkillByUserProfileAndSkill(UserProfile userProfile, Skill skill);
    void deleteByUserProfileAndSkill(UserProfile userProfile, Skill skill);

    Boolean existsUserSkillBySkillAndUserProfile(Skill skill, UserProfile userProfile);

    List<UserSkill> findUserSkillByUserProfile(UserProfile userProfile);

    Boolean existsUserSkillByUserProfile(UserProfile userProfile);
}
