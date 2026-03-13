package com.rohit.job_protal.service;



import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.rohit.job_protal.dto.request.ApplicationStatusDto;
import com.rohit.job_protal.dto.response.*;
import com.rohit.job_protal.entity.JobApplication;
import org.springframework.data.domain.Pageable;

import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.filter.JobFilterDto;
import org.springframework.data.domain.Sort;

public interface JobService {
	public Job createJob(JobDto job);
	public List<JobListingDTO> getJob();
	public JobDetailsDTO getJobDetails(Long id);
	public PageResponse<JobListingDTO> jobSearch(
	        JobFilterDto filterDTO,
	        Pageable pageable
	);

	public List<AppliedUser> getAppliedUser(Pageable pageable, Long id);
	public CandidateResponseDto getCandidateDetails(Long applicationId);
	public HashMap<String, Object> updateApplicationStatus(Long applicationId, ApplicationStatusDto applicationStatusDto);
}
