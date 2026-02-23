package com.rohit.job_protal.security;
import java.time.LocalDateTime;

import com.rohit.job_protal.exception.NotFoundException;
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

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtAuthUtil authUtil;
	
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
	
	public LoginUserResponseDto signupUser(SignupRequestDto user){
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
		       User responseUser =  userRepository.save(saveUser);

	        return mapToReponse(responseUser);
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
