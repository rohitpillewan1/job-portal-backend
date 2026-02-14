package com.rohit.job_protal.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.rohit.job_protal.dto.response.CompanyCreatedResponse;
import com.rohit.job_protal.entity.Company;
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
    public ResponseEntity<SucessApiResponse<CompanyCreatedResponse>> createCompany(
            @RequestBody @Valid CompanyDetailsDTO company) {

        Company companyRes = companyService.createCompany(company);
        CompanyCreatedResponse companyCreatedResponse = new CompanyCreatedResponse();
        companyCreatedResponse.setId(companyRes.getId());
        companyCreatedResponse.setCreatedAt(companyRes.getCreatedAt());
        companyCreatedResponse.setDescription(companyRes.getDescription());
        companyCreatedResponse.setName(companyRes.getName());
        companyCreatedResponse.setLogo_url(companyRes.getLogo_url());
        companyCreatedResponse.setWebsite_url(companyRes.getWebsite_url());

        return ResponseEntity.ok(
                new SucessApiResponse<>(true, "Company created successfully",companyCreatedResponse)
        );
    }
}
