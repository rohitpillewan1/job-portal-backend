package com.rohit.job_protal.controller;
import com.rohit.job_protal.dto.response.JobResponseAppliedDto;
import com.rohit.job_protal.entity.JobApplication;
import com.rohit.job_protal.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;
    @PostMapping("/job/{id}")
    public ResponseEntity<JobResponseAppliedDto> updateJobApplIcationStatus(@PathVariable("id") Long jobId){
      JobApplication jobApplication =  jobApplicationService.upddateJobApplicationStatus(jobId);
       JobResponseAppliedDto jobResponseAppliedDto = new JobResponseAppliedDto();
       jobResponseAppliedDto.setId(jobApplication.getJob().getId());
       jobResponseAppliedDto.setLocation(jobApplication.getJob().getLocation());
       jobResponseAppliedDto.setTitle(jobApplication.getJob().getTitle());
       jobResponseAppliedDto.setHasApplied(true);

       return ResponseEntity.ok().body(jobResponseAppliedDto);
    }
}
