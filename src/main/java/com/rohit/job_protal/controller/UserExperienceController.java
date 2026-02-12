package com.rohit.job_protal.controller;

import com.rohit.job_protal.dto.request.UserExperienceDto;
import com.rohit.job_protal.dto.response.ApiResponse;
import com.rohit.job_protal.service.UserExperienceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/profile")
public class UserExperienceController {
    @Autowired
    private UserExperienceService userExperienceService;
    @PostMapping("/experience")
    public ResponseEntity<ApiResponse> saveUserExperience(@RequestBody @Valid UserExperienceDto userExperienceDto){
        userExperienceService.saveUserExperience(userExperienceDto);
        return ResponseEntity.ok().body(new ApiResponse(true,"User experience updated sucessfully"));
    }
}
