package com.rohit.job_protal.service;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rohit.job_protal.dto.response.JobDetailsDTO;
import com.rohit.job_protal.dto.response.JobDto;
import com.rohit.job_protal.dto.response.JobListingDTO;
import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.filter.JobFilterDto;

public interface JobService {
	public Job createJob(JobDto job);
	public List<JobListingDTO> getJob();
	public JobDetailsDTO getJobDetails(Long id);
	public Page<JobListingDTO> jobSearch(
	        JobFilterDto filterDTO,
	        Pageable pageable
	);
}
