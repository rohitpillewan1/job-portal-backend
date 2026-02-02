package com.rohit.job_protal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserSkillId {

    @Column(name = "user_profile_id")
    private Long userProfileId;

    @Column(name = "skill_id")
    private Long skillId;

    public UserSkillId() {}

    public UserSkillId(Long userProfileId, Long skillId) {
        this.userProfileId = userProfileId;
        this.skillId = skillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSkillId)) return false;
        UserSkillId that = (UserSkillId) o;
        return Objects.equals(userProfileId, that.userProfileId)
                && Objects.equals(skillId, that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfileId, skillId);
    }
}