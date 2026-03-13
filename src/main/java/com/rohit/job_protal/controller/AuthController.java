package com.rohit.job_protal.controller;
import com.rohit.job_protal.dto.request.*;
import com.rohit.job_protal.dto.response.ApiResponse;
import com.rohit.job_protal.dto.response.SignupResponseDto;
import com.rohit.job_protal.dto.response.SucessApiResponse;
import com.rohit.job_protal.service.PhoneVerificationOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rohit.job_protal.dto.response.LoginUserResponseDto;
import com.rohit.job_protal.security.AuthService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {		
		@Autowired
		private AuthService authService;

		@Autowired
		private PhoneVerificationOtpService phoneVerificationOtpService;
		
		@PostMapping("/login")
		public ResponseEntity<SucessApiResponse<LoginUserResponseDto>> loginUser(@Valid @RequestBody LoginRequestDto user){
			LoginUserResponseDto responseUser =  authService.loginUser(user);
			return ResponseEntity.ok().body(new SucessApiResponse<>(true,"User Logged In Successfully",responseUser));
		}
		
		@PostMapping("/signup")
		public ResponseEntity<SucessApiResponse> signUpUser(@Valid @RequestBody SignupRequestDto user){

		SucessApiResponse responseDto =  authService.signupUser(user);
		 return ResponseEntity.ok().body(responseDto);
		}

		@PostMapping("/resend-verification")
		public ResponseEntity<SucessApiResponse> resendVerification(@RequestBody @Valid ResendVerificationDto dto){
			  authService.resendEmailVerification(dto);
			  return ResponseEntity.ok().body(new SucessApiResponse<>(true,"If your email is registered and not verified, a verification link has been sent.",null));
		}

		@PostMapping("/forgot-password")
		public ResponseEntity<SucessApiResponse> sendForgetPasswordLink(@RequestBody @Valid ResendVerificationDto dto){
			authService.forgetPassowrd(dto);
			return ResponseEntity.ok().body(new SucessApiResponse(true,"If your email is registered and not verified, a verification link has been sent.",null));
		}


		@PostMapping("/reset-password")
		public ResponseEntity<SucessApiResponse> resetPassword(@RequestBody @Valid ResetPasswordDto dto){
			authService.resetPassword(dto);
			return  ResponseEntity.ok().body(new SucessApiResponse(true,"Password is updated sucessfully",null));
		}

}
