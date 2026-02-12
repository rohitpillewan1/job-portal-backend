package com.rohit.job_protal.controller;

import com.rohit.job_protal.dto.request.UserSkillDto;
import com.rohit.job_protal.dto.response.ApiResponse;
import com.rohit.job_protal.dto.response.UserSkillDtoReponse;
import com.rohit.job_protal.entity.UserSkill;
import com.rohit.job_protal.service.UserSkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user/profile")
public class UserSkillController {
    @Autowired
    private UserSkillService userSkillService;

    @PostMapping("/skills")
    public ResponseEntity<ApiResponse> saveUserSkills(@RequestBody @Valid Set<UserSkillDto> userSkillDtoSet){
        userSkillService.saveUserSkills(userSkillDtoSet);
        return  ResponseEntity.ok().body(new ApiResponse(true,"User skills updated sucessfully"));
    }
    @PutMapping("/skills")
    public ResponseEntity<ApiResponse> updateUserSkill(
            @RequestBody @Valid UserSkillDto dto) {
        userSkillService.updateUserSkill(dto.getSkillId(), dto);
        return ResponseEntity.ok(new ApiResponse(true, "Skill updated successfully"));
    }
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<ApiResponse> deleteUserSkill(
            @PathVariable("id") Long skillId) {

        userSkillService.deleteUserSkill(skillId);

        return ResponseEntity.ok(new ApiResponse(true, "Skill deleted successfully"));
    }
    @GetMapping("/skills")
    public ResponseEntity<List<UserSkillDtoReponse>> getUserSkill(){
        return ResponseEntity.ok().body(userSkillService.getUserSkill());
    }


}

