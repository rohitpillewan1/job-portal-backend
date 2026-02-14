package com.rohit.job_protal.controller;

import com.rohit.job_protal.dto.request.SignupRequestDto;
import com.rohit.job_protal.service.ProfileStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temp")
public class TempController {

    @Autowired
    private ProfileStatusService profileStatusService;
    @GetMapping("/get")
    public Long getMessage(){

        return profileStatusService.getProfileStatusPercenatage();
    }
}
