package com.rohit.job_protal.controller;
import com.rohit.job_protal.dto.request.UserEducationDto;
import com.rohit.job_protal.dto.response.ApiResponse;
import com.rohit.job_protal.dto.response.SucessApiResponse;
import com.rohit.job_protal.dto.response.UserEducationResponseDto;
import com.rohit.job_protal.service.UserEducationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/profile")
public class UserEducationController {
    @Autowired
    private UserEducationService userEducationService;
    @PostMapping("/education")
    public ResponseEntity<SucessApiResponse<UserEducationResponseDto>> saveUserEducation(@RequestBody @Valid UserEducationDto userEducationDto){
       UserEducationResponseDto userEducationResponseDto =  userEducationService.saveUserEducation(userEducationDto);
         return  ResponseEntity.ok().body(new SucessApiResponse<>(true,"Education updated sucessfully",userEducationResponseDto));
    }

    @GetMapping("/education")
    public ResponseEntity<List<UserEducationResponseDto>> getAllUserDetails(){
        List<UserEducationResponseDto> userEducationResponseDtos = userEducationService.getAllUserEducation();
        return ResponseEntity.ok().body(userEducationResponseDtos);
    }
}
