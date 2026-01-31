package com.rohit.job_protal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.job_protal.entity.Company;
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
	public boolean existsByName(String name);
}
