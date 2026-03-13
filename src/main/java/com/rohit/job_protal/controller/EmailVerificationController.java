package com.rohit.job_protal.controller;

import com.rohit.job_protal.dto.response.SucessApiResponse;
import com.rohit.job_protal.service.EmailVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class EmailVerificationController {
    @Autowired
    EmailVerificationTokenService verifyEmailService;
    @GetMapping("/verify")
    public ResponseEntity<SucessApiResponse<String>> verifyEmail(
            @RequestParam String token) {

       verifyEmailService.verifyEmail(token);

        return ResponseEntity.ok(
                new SucessApiResponse<>(
                        true,
                        "Email verified successfully. You can now login.",
                        null
                )
        );
    }
}
