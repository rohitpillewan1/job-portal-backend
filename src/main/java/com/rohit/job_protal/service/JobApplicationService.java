package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.response.AppliedJobResponseDTO;
import com.rohit.job_protal.entity.JobApplication;

import java.util.List;

public interface JobApplicationService {
    public JobApplication updateJobApplicationStatus(Long jobId);
    public List<AppliedJobResponseDTO> getUserAppliedJob();
}
