package com.rohit.job_protal.controller;
import com.rohit.job_protal.dto.request.UserResumeDto;
import com.rohit.job_protal.dto.response.ApiResponse;
import com.rohit.job_protal.security.SecurityUtil;
import com.rohit.job_protal.service.UserResumeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/profile")
public class UserResumeController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private UserResumeService userResumeService;

    @PostMapping("/resume")
    public ResponseEntity<ApiResponse> saveResume(@RequestBody @Valid  UserResumeDto resumeDto){
        userResumeService.saveUserResume(resumeDto);
        return ResponseEntity.ok().body(new ApiResponse(true,"resume update sucessfully"));
    }
}
