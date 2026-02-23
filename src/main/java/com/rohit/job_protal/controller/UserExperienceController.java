package com.rohit.job_protal.controller;

import com.rohit.job_protal.dto.request.UserExperienceDto;
import com.rohit.job_protal.dto.response.ApiResponse;
import com.rohit.job_protal.dto.response.SucessApiResponse;
import com.rohit.job_protal.dto.response.UserExperienceResponseDto;
import com.rohit.job_protal.service.UserExperienceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/profile")
public class UserExperienceController {
    @Autowired
    private UserExperienceService userExperienceService;
    @PostMapping("/experience")
    public ResponseEntity<SucessApiResponse<UserExperienceResponseDto>> saveUserExperience(@RequestBody @Valid UserExperienceDto userExperienceDto){
       UserExperienceResponseDto responseDto =  userExperienceService.saveUserExperience(userExperienceDto);
        return ResponseEntity.ok().body(new SucessApiResponse<>(true,"Experience Updated Sucessfully",responseDto));
    }

    @GetMapping("/experiences")
    public ResponseEntity<SucessApiResponse<List<UserExperienceResponseDto>>> getAllUserExperiences(){
        List<UserExperienceResponseDto> responseDtoList = userExperienceService.getAllUserExperience();
        return ResponseEntity.ok().body(new SucessApiResponse<>(true,"All Experience fetch successfully",responseDtoList));
    }

    @PutMapping("/experience/{id}")
    public ResponseEntity<SucessApiResponse<UserExperienceResponseDto>> updateUserExperience(@PathVariable("id") Long id,@RequestBody @Valid UserExperienceDto userExperienceDto){
       System.out.println("Hello world");
       System.out.println(userExperienceDto.toString());
        UserExperienceResponseDto responseDto = userExperienceService.updateUserExperience(id,userExperienceDto);
       return ResponseEntity.ok().body(new SucessApiResponse<>(true,"Experience Update Sucessfully",responseDto));
    }

    @DeleteMapping("/experience/{id}")
    public ResponseEntity<ApiResponse> deleteUserExperience(@PathVariable("id") Long id){
        userExperienceService.deleteUserExperience(id);
        return ResponseEntity.ok().body(new ApiResponse(true,"User Experience deleted sucessfully"));
    }
}
