package com.rohit.job_protal.entity;

import com.rohit.job_protal.enums.Proficiency;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(
        name = "user_skills",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_profile_id", "skill_id"})
        }
)
@Data
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    private Integer experience;

    @Enumerated(EnumType.STRING)
    private Proficiency proficiency;
}
