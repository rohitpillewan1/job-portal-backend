package com.rohit.job_protal.controller;

import java.util.HashMap;
import java.util.Map;

import com.rohit.job_protal.dto.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.job_protal.dto.response.JobListingDTO;
import com.rohit.job_protal.filter.JobFilterDto;
import com.rohit.job_protal.service.JobService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/job")
public class JobListingController {
	
	@Autowired
	private JobService jobService;
	
	@PostMapping("/job-listing")
	public ResponseEntity<PageResponse<JobListingDTO>> getJobs(
	        @RequestBody(required = false) JobFilterDto filterDTO,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size
	) {

	    if (filterDTO == null) {
	        filterDTO = new JobFilterDto(); // empty filter
	    }

	    Pageable pageable = PageRequest.of(page, size);
	    return ResponseEntity.ok(
	            jobService.jobSearch(filterDTO, pageable)
	    );
	}
	
	@GetMapping("/job-details/{id}")
	public ResponseEntity<Map<String,Object>> getJobForDetails(@PathVariable("id")long id){
		HashMap<String, Object> map = new HashMap<>();
		map.put("sucess",true);
		map.put("message","Job details fetched successfully");
		map.put("job",jobService.getJobDetails(id));
		return ResponseEntity.ok(map);
	}

}
