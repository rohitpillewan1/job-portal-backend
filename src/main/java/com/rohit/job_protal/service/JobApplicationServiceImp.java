package com.rohit.job_protal.service;

import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.entity.JobApplication;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.enums.ApplicationStatus;
import com.rohit.job_protal.exception.JobAlreadyApplied;
import com.rohit.job_protal.exception.JobNotFound;
import com.rohit.job_protal.repository.JobApplicationRepository;
import com.rohit.job_protal.repository.JobRepository;
import com.rohit.job_protal.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobApplicationServiceImp implements JobApplicationService{

    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    @Override
    public JobApplication upddateJobApplicationStatus(Long jobId) {
        User user = securityUtil.getCurrentUser();
        Job job = jobRepository.findById(jobId).orElseThrow(()->new JobNotFound("Job not found"));
        if(jobApplicationRepository.existsJobApplicationByUserAndJob(user,job)){
            throw new JobAlreadyApplied("Job Already Applied");
        }
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUser(user);
        jobApplication.setJob(job);
        jobApplication.setAppliedAt(LocalDateTime.now());
        jobApplication.setApplicationStatus(ApplicationStatus.APPLIED);
        return jobApplicationRepository.save(jobApplication);

    }
}
