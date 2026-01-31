package com.rohit.job_protal.controller;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rohit.job_protal.dto.response.JobDto;
import com.rohit.job_protal.dto.response.JobListingDTO;
import com.rohit.job_protal.dto.response.SucessApiResponse;
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


}
