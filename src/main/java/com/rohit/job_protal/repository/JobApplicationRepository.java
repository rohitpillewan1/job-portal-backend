package com.rohit.job_protal.repository;

import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.entity.JobApplication;
import com.rohit.job_protal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
    Boolean existsJobApplicationByUserAndJob(User user, Job job);
}
