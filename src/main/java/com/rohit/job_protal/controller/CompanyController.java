package com.rohit.job_protal.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.job_protal.dto.response.ApiResponse;
import com.rohit.job_protal.dto.response.CompanyDetailsDTO;
import com.rohit.job_protal.dto.response.SucessApiResponse;
import com.rohit.job_protal.service.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/companies")
    public ResponseEntity<SucessApiResponse<LinkedHashMap<String, Object>>> createCompany(
            @RequestBody @Valid CompanyDetailsDTO company) {

        Long companyId = companyService.createCompany(company);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id",companyId);
        map.put("name",company.getName());

        return ResponseEntity.ok(
                new SucessApiResponse<>(true, "Company created successfully",map)
        );
    }
}
