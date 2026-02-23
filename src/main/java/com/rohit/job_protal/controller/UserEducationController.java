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
         return  ResponseEntity.ok().body(new SucessApiResponse<>(true,"Education updated successfully",userEducationResponseDto));
    }

    @GetMapping("/education")
    public ResponseEntity<SucessApiResponse<List<UserEducationResponseDto>>> getAllUserDetails(){
        List<UserEducationResponseDto> userEducationResponseDtos = userEducationService.getAllUserEducation();
        return ResponseEntity.ok().body(new SucessApiResponse<>(true,"Fetch All user education",userEducationResponseDtos));
    }

    @PutMapping("/education/{id}")
    public ResponseEntity<SucessApiResponse<UserEducationResponseDto>> updateEducation(@PathVariable("id") Long id ,@RequestBody @Valid UserEducationDto userEducationDto){
            UserEducationResponseDto userEducationResponseDto = userEducationService.updateUserEducation(id,userEducationDto);
            return ResponseEntity.ok().body(new SucessApiResponse<>(true,"User education successfully updated",userEducationResponseDto));
    }

    @DeleteMapping("/education/{id}")
    public ResponseEntity<ApiResponse> deleteUserEducation(@PathVariable("id") Long id){
        userEducationService.deleteUserEducation(id);
        return ResponseEntity.ok().body(new ApiResponse(true,"User delted sucessfully"));
    }
}
