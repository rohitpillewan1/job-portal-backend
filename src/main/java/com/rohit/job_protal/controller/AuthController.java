package com.rohit.job_protal.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rohit.job_protal.dto.request.LoginRequestDto;
import com.rohit.job_protal.dto.request.SignupRequestDto;
import com.rohit.job_protal.dto.response.UserResponseDto;
import com.rohit.job_protal.security.AuthService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {		
		@Autowired
		private AuthService authService;
		
		@PostMapping("/login")
		public ResponseEntity<UserResponseDto> loginUser(@RequestBody LoginRequestDto user){
			UserResponseDto responseUser =  authService.loginUser(user);
			return ResponseEntity.ok().body(responseUser);
		}
		
		@PostMapping("/signup")
		public ResponseEntity<?> signUpUser(@Valid @RequestBody SignupRequestDto user){
		  System.out.println("Hello");
		  return authService.signupUser(user);
		}

}
