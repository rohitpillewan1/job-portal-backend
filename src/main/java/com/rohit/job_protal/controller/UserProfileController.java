package com.rohit.job_protal.controller;
import com.rohit.job_protal.dto.request.UserProfileDto;
import com.rohit.job_protal.dto.response.SucessApiResponse;
import com.rohit.job_protal.dto.response.UserProfileResponseDto;
import com.rohit.job_protal.service.ProfileStatusService;
import com.rohit.job_protal.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ProfileStatusService profileStatusService;

    @PostMapping("/basic")
    public ResponseEntity<SucessApiResponse<UserProfileResponseDto>> createBasicProfile(
            @Valid @RequestBody UserProfileDto userProfileDto) {
        UserProfileResponseDto response = userProfileService.saveUserProfile(userProfileDto);
        return ResponseEntity.ok().body(new SucessApiResponse<>(true,"Profile created sucessfully",response));
    }
    @GetMapping("/basic")
    public ResponseEntity<SucessApiResponse<UserProfileResponseDto>> getUserProfile(){
        UserProfileResponseDto userProfile = userProfileService.getUserProfile();
        return ResponseEntity.ok().body(new SucessApiResponse<>(true,"Profile fetch sucessfully",userProfile));
    }

}
