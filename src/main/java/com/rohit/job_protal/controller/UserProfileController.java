package com.rohit.job_protal.controller;
import com.rohit.job_protal.dto.request.UserProfileDto;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/basic")
    public ResponseEntity<UserProfileDto> createBasicProfile(
            @Valid @RequestBody UserProfileDto userProfileDto) {

        UserProfile savedProfile = userProfileService.saveUserProfile(userProfileDto);

        UserProfileDto response = new UserProfileDto();
        response.setGender(savedProfile.getGender());
        response.setPhone(savedProfile.getPhone());
        response.setCity(savedProfile.getCity());
        response.setState(savedProfile.getState());
        response.setCountry(savedProfile.getCountry());
        response.setTotalExperience(savedProfile.getTotalExperience());

        return ResponseEntity.ok(response);
    }
}
