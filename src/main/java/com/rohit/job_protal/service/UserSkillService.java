package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.request.UserSkillDto;
import com.rohit.job_protal.dto.response.UserSkillDtoReponse;
import com.rohit.job_protal.entity.UserSkill;


import java.util.List;
import java.util.Set;

public interface UserSkillService {
    public void saveUserSkills(Set<UserSkillDto> userSkillDtoSet);
    public void updateUserSkill(Long skillId, UserSkillDto userSkillDto);
    public void deleteUserSkill(Long skillId);
    public List<UserSkillDtoReponse> getUserSkill();
}
