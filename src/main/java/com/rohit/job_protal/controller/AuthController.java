package com.rohit.job_protal.controller;
import com.rohit.job_protal.dto.response.SucessApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rohit.job_protal.dto.request.LoginRequestDto;
import com.rohit.job_protal.dto.request.SignupRequestDto;
import com.rohit.job_protal.dto.response.LoginUserResponseDto;
import com.rohit.job_protal.security.AuthService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {		
		@Autowired
		private AuthService authService;
		
		@PostMapping("/login")
		public ResponseEntity<SucessApiResponse<LoginUserResponseDto>> loginUser(@Valid @RequestBody LoginRequestDto user){
			LoginUserResponseDto responseUser =  authService.loginUser(user);
			return ResponseEntity.ok().body(new SucessApiResponse<>(true,"User Logged In Successfully",responseUser));
		}
		
		@PostMapping("/signup")
		public ResponseEntity<SucessApiResponse<LoginUserResponseDto>> signUpUser(@Valid @RequestBody SignupRequestDto user){

		 LoginUserResponseDto responseDto =  authService.signupUser(user);
		 return ResponseEntity.ok().body(new SucessApiResponse<>(true,"User Registered Successfully",responseDto));
		}

}
