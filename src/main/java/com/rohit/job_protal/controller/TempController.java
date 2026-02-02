package com.rohit.job_protal.controller;

import com.rohit.job_protal.dto.request.SignupRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temp")
public class TempController {

    @PostMapping("/get")
    public SignupRequestDto getMessage(@RequestBody SignupRequestDto user){
        return user;
    }
}
