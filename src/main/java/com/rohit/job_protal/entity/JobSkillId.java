package com.rohit.job_protal.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class JobSkillId implements Serializable {

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "skill_id")
    private Long skillId;

    // 🔴 VERY IMPORTANT
    public JobSkillId() {}

    public JobSkillId(Long jobId, Long skillId) {
        this.jobId = jobId;
        this.skillId = skillId;
    }

    // equals & hashCode (MANDATORY)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobSkillId)) return false;
        JobSkillId that = (JobSkillId) o;
        return Objects.equals(jobId, that.jobId) &&
               Objects.equals(skillId, that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, skillId);
    }
}

