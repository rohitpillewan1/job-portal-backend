package com.rohit.job_protal.security;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.rohit.job_protal.dto.request.ResendVerificationDto;
import com.rohit.job_protal.dto.request.ResetPasswordDto;
import com.rohit.job_protal.dto.response.ApiResponse;
import com.rohit.job_protal.dto.response.SignupResponseDto;
import com.rohit.job_protal.dto.response.SucessApiResponse;
import com.rohit.job_protal.entity.EmailVerificationToken;
import com.rohit.job_protal.entity.PasswordResetToken;
import com.rohit.job_protal.exception.NotFoundException;
import com.rohit.job_protal.exception.TokenIsExpired;
import com.rohit.job_protal.exception.UnAuthorizedException;
import com.rohit.job_protal.repository.EmailVerificationTokenRepository;
import com.rohit.job_protal.repository.PasswordResetTokenReposiroty;
import com.rohit.job_protal.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rohit.job_protal.dto.request.LoginRequestDto;
import com.rohit.job_protal.dto.request.SignupRequestDto;
import com.rohit.job_protal.dto.response.LoginUserResponseDto;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.enums.AuthProvider;
import com.rohit.job_protal.enums.Role;
import com.rohit.job_protal.exception.UserAlreadyExist;
import com.rohit.job_protal.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtAuthUtil authUtil;

	@Autowired
	private EmailVerificationTokenRepository emailVerificationRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordResetTokenReposiroty passwordResetTokenReposiroty;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	public LoginUserResponseDto loginUser(LoginRequestDto userDto){
		if(!userRepository.existsByEmail(userDto.getEmail())){
			throw new NotFoundException("Email is not register use register email");
		}
		System.out.println(userDto.toString());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user=(User)authentication.getPrincipal();
		return mapToReponse(user);
	}	
	
	public SucessApiResponse signupUser(SignupRequestDto user){
	    	 if(userRepository.existsByEmail(user.getEmail())) {
		            throw new UserAlreadyExist("User is already exists");
		        }
		        User saveUser = new User();
		        saveUser.setEmail(user.getEmail());
		        saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
		        saveUser.setName(user.getName());
		        saveUser.setRole(Role.USER);
		        saveUser.setAuthProvider(AuthProvider.LOCAL);
		        saveUser.setCreatedAt(LocalDateTime.now()); 
		        saveUser.setUpdatedAt(LocalDateTime.now());
				saveUser.setEnabled(false);
		       User responseUser =  userRepository.save(saveUser);
			   emailVerification(responseUser);

	        return new SucessApiResponse(true,"Verification email sent. Please verify before login.",null);
	}

	@Transactional
	public void resendEmailVerification(ResendVerificationDto dto){

		Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
		if(optionalUser.isEmpty()){
			return;
		}

		User user = optionalUser.get();

		if(user.isEnabled()){
			return;
		}

		Optional<EmailVerificationToken> existing =
				emailVerificationRepo.findByUser(user);

		existing.ifPresent(token -> {
			emailVerificationRepo.delete(token);
			emailVerificationRepo.flush();  // force delete immediately
		});

		emailVerification(user);
	}

	private void emailVerification(User responseUser){
		String token = UUID.randomUUID().toString();
		EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
		emailVerificationToken.setUser(responseUser);
		emailVerificationToken.setToken(token);
		emailVerificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
		emailVerificationToken.setUsed(false);

		emailVerificationRepo.save(emailVerificationToken);
		System.out.println("Verify link:");
		System.out.println("http://localhost:8080/api/auth/verify?token=" + token);
		emailService.sendEmailVerification(responseUser,token);
	}


	@Transactional
	public void forgetPassowrd(@RequestBody ResendVerificationDto userDto){
		Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
		if(optionalUser.isEmpty()){return;}
		User user = optionalUser.get();
		if(passwordResetTokenReposiroty.findByUser(user).isPresent()){
			passwordResetTokenReposiroty.deleteAllByUser(user);
		}

		String token = UUID.randomUUID().toString();
		PasswordResetToken newPasswordResetToken = new PasswordResetToken();
		newPasswordResetToken.setUser(user);
		newPasswordResetToken.setToken(token);
		newPasswordResetToken.setExpiryDate(LocalDateTime.now().plusHours(15));
		newPasswordResetToken.setUsed(false);
		passwordResetTokenReposiroty.save(newPasswordResetToken);

		emailService.sendEmailPasswordReset(user,token);


	}

	@Transactional
	public void resetPassword(ResetPasswordDto dto){
		System.out.println(dto.getToken());
		Optional<PasswordResetToken> optionalToken = passwordResetTokenReposiroty.findByToken(dto.getToken());
		System.out.println(optionalToken);
		if(optionalToken.isEmpty()){
			throw new UnAuthorizedException("Token is invalid");
		}
		PasswordResetToken passwordResetToken = optionalToken.get();
		if(passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())){
			throw new TokenIsExpired("token is expired");
		}

		if(passwordResetToken.isUsed()){
			throw new UnAuthorizedException("Token is already in used");
		}
		User user = passwordResetToken.getUser();

		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		userRepository.save(user);

		passwordResetToken.setUsed(true);
		passwordResetTokenReposiroty.save(passwordResetToken);
	}




	private String getAccessToken(User user){

		return authUtil.generateAccessToken(user);
	}



	public LoginUserResponseDto mapToReponse(User user){
		LoginUserResponseDto LoginUserResponseDto = new LoginUserResponseDto();
		LoginUserResponseDto.setId(user.getId());
		LoginUserResponseDto.setName(user.getName());
		LoginUserResponseDto.setEmail(user.getEmail());
		LoginUserResponseDto.setJwtToken(getAccessToken(user));
		LoginUserResponseDto.setRole(user.getRole());

		return LoginUserResponseDto;
	}
}
