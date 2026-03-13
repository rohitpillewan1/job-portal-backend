package com.rohit.job_protal.service;

import com.rohit.job_protal.entity.PhoneVerificationOtp;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.exception.*;
import com.rohit.job_protal.repository.PhoneVerificationOtpRepository;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.security.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PhoneVerificationOtpServiceImp implements PhoneVerificationOtpService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PhoneVerificationOtpRepository phoneVerificationOtpRepository;


    @Transactional
    @Override
    public void generateOtp() {
       UserProfile userProfile = getUserProfile();
        if (userProfile.isPhoneVerified()){
            throw new TokenIsUsed("Phone number is already verified");
        }
       Optional<PhoneVerificationOtp> optionalOtp = phoneVerificationOtpRepository.findByUserProfile(userProfile);


       if(optionalOtp.isPresent()){
           PhoneVerificationOtp phoneVerification = optionalOtp.get();
           if(phoneVerification.isUsed()){
               throw new TokenIsUsed("Phone Number is already verify");
           }
           phoneVerificationOtpRepository.deleteByUserProfile(userProfile);
           phoneVerificationOtpRepository.flush();
       }

       int num = (int)(Math.random()*900000)+100000;
       String otp = String.valueOf(num);
        PhoneVerificationOtp phoneVerificationOtp = new PhoneVerificationOtp();
        phoneVerificationOtp.setUsed(false);
        phoneVerificationOtp.setOtp(passwordEncoder.encode(otp));
        phoneVerificationOtp.setUserProfile(userProfile);
        phoneVerificationOtp.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        System.out.println(otp);
        phoneVerificationOtpRepository.save(phoneVerificationOtp);
    }

    @Override
    public void verifyOtp(String otp) {
        UserProfile userProfile = getUserProfile();
        if (userProfile.isPhoneVerified()){
            throw new TokenIsUsed("Phone number is already verified");
        }
       Optional<PhoneVerificationOtp> OptionalPhoneVerificationOtp = phoneVerificationOtpRepository.findByUserProfile(userProfile);
        if(OptionalPhoneVerificationOtp.isEmpty()){
            throw new NotFoundException("Otp Not Found");
        }
        PhoneVerificationOtp phoneVerificationOtp = OptionalPhoneVerificationOtp.get();
        if(phoneVerificationOtp.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new TokenIsExpired("Otp is expired please resend OTP");
        }
        if(!passwordEncoder.matches(otp,phoneVerificationOtp.getOtp())){
            throw new InvalidOtp("Invalid Otp");
        }

       phoneVerificationOtp.setUsed(true);
       userProfile.setPhoneVerified(true);
       userProfileRepository.save(userProfile);
       phoneVerificationOtpRepository.save(phoneVerificationOtp);

    }

    public UserProfile getUserProfile(){
        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findUserProfileByUser(user);
        if(userProfile==null){
            throw new UserProfileNotFound("User profile not found");
        }
        return userProfile;
    }




}
