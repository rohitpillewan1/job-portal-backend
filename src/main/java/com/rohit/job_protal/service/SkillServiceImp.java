package com.rohit.job_protal.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.job_protal.dto.response.SkillDTO;
import com.rohit.job_protal.entity.Skill;
import com.rohit.job_protal.exception.SkillAlreadyExist;
import com.rohit.job_protal.repository.SkillsRepository;

@Service
public class SkillServiceImp implements SkillService {
	
	@Autowired
	private SkillsRepository skillsRepository;
	@Override
	public long createSkill(SkillDTO skillDTO) {
		if(skillsRepository.existsByNameIgnoreCase(skillDTO.getName())) {
			throw new SkillAlreadyExist("Skill already present");
		}
		Skill skill = new Skill();
		skill.setName(skillDTO.getName());
		skill.setCreatedAt(LocalDateTime.now());
		skill.setUpdatedAt(LocalDateTime.now());
		return skillsRepository.save(skill).getId();
	}

}
