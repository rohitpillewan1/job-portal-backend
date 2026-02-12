package com.rohit.job_protal.controller;
import com.rohit.job_protal.dto.request.UserEducationDto;
import com.rohit.job_protal.dto.response.ApiResponse;
import com.rohit.job_protal.service.UserEducationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/profile")
public class UserEducationController {
    @Autowired
    private UserEducationService userEducationService;
    @PostMapping("/education")
    public ResponseEntity<ApiResponse> saveUserEducation(@RequestBody @Valid UserEducationDto userEducationDto){
         userEducationService.saveUserEducation(userEducationDto);
         return  ResponseEntity.ok().body(new ApiResponse(true,"Education update sucessfully"));
    }
}
