package com.rohit.job_protal.controller;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rohit.job_protal.dto.request.ApplicationStatusDto;
import com.rohit.job_protal.dto.response.*;
import com.rohit.job_protal.entity.JobApplication;
import com.rohit.job_protal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.filter.JobFilterDto;
import com.rohit.job_protal.service.JobService;

@RestController
@RequestMapping("/api/admin")
public class JobController {
	
	@Autowired
	private JobService jobService;
	@PostMapping("/job")
	public ResponseEntity<SucessApiResponse<LinkedHashMap<String, Object>>> createJob(@RequestBody JobDto job){
		Job job1 = jobService.createJob(job);
		LinkedHashMap<String,Object> map = new LinkedHashMap<>();
		map.put("id", job1.getId());
		map.put("name",job1.getTitle());
		return ResponseEntity.ok(
				new SucessApiResponse<>(true,"Job created successfully",map)
				);
	}
	
//	@GetMapping("/jobs")
	public ResponseEntity<Map<String,Object>> getJobForListing(){
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("sucess",true);
		map.put("message","Job details fetched successfully");
		map.put("jobs",jobService.getJob());
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/jobs/{id}/applicants")
	public ResponseEntity<SucessApiResponse<List<AppliedUser>>> getJobApplicant(@RequestParam(defaultValue = "0",required = false) int page,
																				@RequestParam(defaultValue = "10",required = false) int pageSize,
																				@PathVariable("id") Long id,
																				@RequestParam(defaultValue = "appliedAt",required = false) String sortBy,
																				@RequestParam(defaultValue = "ASC",required = false) String sortDir){
		Sort sort=null;
		if(sortDir.equals("ASC")){
			sort=Sort.by(sortBy).ascending();
		}else{
			sort=Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(page,pageSize,sort);
			List<AppliedUser> userList = jobService.getAppliedUser(pageable,id);
			return ResponseEntity.ok().body(new SucessApiResponse<>(true,"Applied jobs",userList));
	}

	@GetMapping("/jobs/applicants/{applicationId}")
	public ResponseEntity<SucessApiResponse<CandidateResponseDto>> getCandidateDetails(@PathVariable("applicationId") Long applicationId){
		CandidateResponseDto candidateResponseDto = jobService.getCandidateDetails(applicationId);
		return ResponseEntity.ok().body(new SucessApiResponse<>(true,"Candidate Details",candidateResponseDto));
	}

	@PatchMapping("/job/application/{applicationId}/status")
	public ResponseEntity<SucessApiResponse<HashMap<String,Object>>> updateApplicationStatusReq(@PathVariable("applicationId") Long applicationId , @RequestBody  ApplicationStatusDto applicationStatusDto){
		HashMap<String, Object> map = jobService.updateApplicationStatus(applicationId,applicationStatusDto);
		return ResponseEntity.ok().body(new SucessApiResponse<>(true,"Application status updated",map));
	}


}
