package com.rohit.job_protal.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.job_protal.dto.response.CompanyDetailsDTO;
import com.rohit.job_protal.entity.Company;
import com.rohit.job_protal.exception.CompanyAlreadyExistException;
import com.rohit.job_protal.repository.CompanyRepository;

@Service
public class CompanyServiceImp implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public long createCompany(CompanyDetailsDTO dto) {

        if (companyRepository.existsByName(dto.getName())) {
            throw new CompanyAlreadyExistException(
                "Company with name '" + dto.getName() + "' already exists"
            );
        }

        Company company = new Company();
        company.setName(dto.getName());
        company.setLogo_url(dto.getLogoUrl());
        company.setDescription(dto.getDescription());
        company.setWebsite_url(dto.getWebsiteUrl());
        company.setCreatedAt(LocalDateTime.now());
        company.setUpdatedAt(LocalDateTime.now());

        companyRepository.save(company);
        return company.getId();
    }
}
