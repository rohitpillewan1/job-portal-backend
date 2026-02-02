package com.rohit.job_protal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_skills")
@Data
public class UserSkill {

    @EmbeddedId
    private UserSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userProfileId")
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private Integer experience;   // years
    private String proficiency;   // BEGINNER / INTERMEDIATE / EXPERT
}
