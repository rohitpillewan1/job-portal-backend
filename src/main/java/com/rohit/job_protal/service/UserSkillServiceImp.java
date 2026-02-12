package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserSkillDto;
import com.rohit.job_protal.dto.response.UserSkillDtoReponse;
import com.rohit.job_protal.entity.*;
import com.rohit.job_protal.exception.EndDateNotePresent;
import com.rohit.job_protal.exception.SkillAlreadyExist;
import com.rohit.job_protal.exception.UserProfileNotFound;
import com.rohit.job_protal.repository.SkillsRepository;
import com.rohit.job_protal.repository.UserProfileRepository;
import com.rohit.job_protal.repository.UserSkillRepository;
import com.rohit.job_protal.security.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Service
public class UserSkillServiceImp implements UserSkillService{

    @Autowired
    private SkillsRepository skillsRepository;
    @Autowired
    private UserSkillRepository userSkillRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private SecurityUtil securityUtil;
    @Override
    @Transactional
    public void saveUserSkills(Set<UserSkillDto> dtos) {

        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findByUser(user);

        if (userProfile == null) {
            throw new UserProfileNotFound("Create profile first");
        }

        for (UserSkillDto dto : dtos) {

            Skill skill = skillsRepository.findById(dto.getSkillId())
                    .orElseThrow(() -> new EndDateNotePresent("Skill not found"));
            if(userSkillRepository.existsUserSkillByUserProfileAndSkill(userProfile,skill)){
                throw new SkillAlreadyExist("user skill already present");
            }
                UserSkill newSkill = new UserSkill();
                newSkill.setUserProfile(userProfile);
                newSkill.setSkill(skill);
                newSkill.setExperience(dto.getExperience());
                newSkill.setProficiency(dto.getProficiency());

                userSkillRepository.save(newSkill);
        }
    }

    @Override
    public void updateUserSkill(Long skillId, UserSkillDto userSkillDto) {
        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findByUser(user);
        if(userProfile==null){
            throw new UserProfileNotFound("User profile not found first create user profile");
        }
        Skill skill = skillsRepository.findById(skillId).orElseThrow(()->new EndDateNotePresent("Skill not found"));
        UserSkill userSkill = userSkillRepository.findByUserProfileAndSkill(userProfile,skill).orElseThrow(()->new EndDateNotePresent("User skill not found"));
        userSkill.setProficiency(userSkillDto.getProficiency());
        userSkill.setExperience(userSkillDto.getExperience());

        userSkillRepository.save(userSkill);
    }

    @Transactional
    public void deleteUserSkill(Long skillId) {
        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findByUser(user);
        if(userProfile==null){
            throw new UserProfileNotFound("User profile not found first create user profile");
        }
        Skill skill = skillsRepository.findById(skillId).orElseThrow(()->new EndDateNotePresent("Skill not found"));
        if(!userSkillRepository.existsUserSkillBySkillAndUserProfile(skill,userProfile)){
            throw new EndDateNotePresent("User skill not present");
        }
         userSkillRepository.deleteByUserProfileAndSkill(userProfile,skill);

    }

    @Override
    public List<UserSkillDtoReponse> getUserSkill() {
       User user = securityUtil.getCurrentUser();
       UserProfile userProfile = userProfileRepository.findByUser(user);
       if(userProfile==null){
           throw new UserProfileNotFound("User profile not found");
       }
       List<UserSkill> userSkill=userSkillRepository.findUserSkillByUserProfile(userProfile);
       List<UserSkillDtoReponse> userSkillDtos = new ArrayList<>();
       for(UserSkill userSkill1 : userSkill){
           UserSkillDtoReponse dtoResponse = new UserSkillDtoReponse();
           dtoResponse.setId(userSkill1.getSkill().getId());
           dtoResponse.setName(userSkill1.getSkill().getName());
           dtoResponse.setProficiency(userSkill1.getProficiency());
           dtoResponse.setExperience(userSkill1.getExperience());
           userSkillDtos.add(dtoResponse);

       }
       return userSkillDtos;

    }


}
