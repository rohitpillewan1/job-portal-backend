package com.rohit.job_protal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.job_protal.entity.JobSkill;
import com.rohit.job_protal.entity.JobSkillId;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
    List<JobSkill> findByJobId(Long jobId);

    void deleteByJobId(Long jobId);
}
