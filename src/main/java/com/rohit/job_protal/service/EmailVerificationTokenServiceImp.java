package com.rohit.job_protal.service;

import com.rohit.job_protal.entity.EmailVerificationToken;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.exception.NotFoundException;
import com.rohit.job_protal.exception.TokenIsExpired;
import com.rohit.job_protal.exception.TokenIsUsed;
import com.rohit.job_protal.repository.EmailVerificationTokenRepository;
import com.rohit.job_protal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailVerificationTokenServiceImp implements EmailVerificationTokenService{
    @Autowired
    private EmailVerificationTokenRepository emailVerifyTokenRepo;

    @Autowired
    private UserRepository userRepository;
    @Override
    public void verifyEmail(String token) {
       Optional<EmailVerificationToken> optional = emailVerifyTokenRepo.findByToken(token);
       if (optional.isEmpty()){
           throw new NotFoundException("Invalid Token");
       }
       EmailVerificationToken verificationToken = optional.get();
       if(verificationToken.isUsed()){
           throw new TokenIsUsed("Token already in use");
       }
       if(verificationToken.getExpiryDate().isBefore(LocalDateTime.now())){
           throw new TokenIsExpired("Token is expired");
       }
       User user = verificationToken.getUser();
       user.setEnabled(true);
       userRepository.save(user);

       verificationToken.setUsed(true);
       emailVerifyTokenRepo.save(verificationToken);

    }
}
