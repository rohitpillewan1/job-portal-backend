package com.rohit.job_protal.controller;

import com.rohit.job_protal.dto.request.OtpDto;
import com.rohit.job_protal.dto.response.SucessApiResponse;
import com.rohit.job_protal.service.PhoneVerificationOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
public class PhoneVerificationController {

    @Autowired
    private PhoneVerificationOtpService phoneVerificationOtpService;

    @GetMapping("/get-otp")
    public ResponseEntity<SucessApiResponse> generateOtp(){
        phoneVerificationOtpService.generateOtp();
        return  ResponseEntity.ok().body(new SucessApiResponse(true,"Otp sent to your mobile number",null));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<SucessApiResponse> verifyOtp(@RequestBody OtpDto otpDto){
        phoneVerificationOtpService.verifyOtp(otpDto.getOtp());
        return ResponseEntity.ok().body(new SucessApiResponse(true,"otp verify sucessfully",null));
    }
}
