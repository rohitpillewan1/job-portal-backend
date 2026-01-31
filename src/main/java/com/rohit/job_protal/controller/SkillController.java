package com.rohit.job_protal.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.job_protal.dto.response.SkillDTO;
import com.rohit.job_protal.dto.response.SucessApiResponse;
import com.rohit.job_protal.service.SkillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class SkillController {
	
	@Autowired
	private SkillService skillService;
	@PostMapping("/skill")
	public ResponseEntity<SucessApiResponse<LinkedHashMap<String, Object>>> createSkill(@RequestBody @Valid SkillDTO skillDTO){
		long skillId = skillService.createSkill(skillDTO);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id",skillId);
        map.put("name",skillDTO.getName());
		return ResponseEntity.ok().body(
				new SucessApiResponse<>(true,"Skill create sucessfully",map)
				);
	}
}
