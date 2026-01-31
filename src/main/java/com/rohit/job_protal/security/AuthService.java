package com.rohit.job_protal.security;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rohit.job_protal.dto.request.LoginRequestDto;
import com.rohit.job_protal.dto.request.SignupRequestDto;
import com.rohit.job_protal.dto.response.UserResponseDto;
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
	public UserResponseDto loginUser(LoginRequestDto userDto){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user=(User)authentication.getPrincipal();
		System.out.println(user.toString());
		String token=authUtil.generateAccessToken(user);
		UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setId(user.getId());
		userResponseDto.setName(user.getName());
		userResponseDto.setJwtToken(token);
		
		return userResponseDto;
	}	
	
	public ResponseEntity<String> signupUser(SignupRequestDto user){
	    	 if(userRepository.existsByEmail(user.getEmail())) {
		            throw new UserAlreadyExist("User is already exisits");
		        }
		        User saveUser = new User();
		        saveUser.setEmail(user.getEmail());
		        saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
		        saveUser.setName(user.getName());
		        saveUser.setRole(Role.USER);
		        saveUser.setAuthProvider(AuthProvider.LOCAL);
		        saveUser.setCreatedAt(LocalDateTime.now()); 
		        saveUser.setUpdatedAt(LocalDateTime.now()); 
		        userRepository.save(saveUser);
	        return ResponseEntity.ok("User register successfully"); 
	}
}
