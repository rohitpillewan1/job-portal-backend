package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.response.CompanyDetailsDTO;
import com.rohit.job_protal.entity.Company;
import com.rohit.job_protal.exception.CompanyAlreadyExistException;

public interface CompanyService {
	public Company createCompany(CompanyDetailsDTO company);
}
