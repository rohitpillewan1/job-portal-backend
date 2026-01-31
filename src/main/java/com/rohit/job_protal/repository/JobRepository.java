package com.rohit.job_protal.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.enums.EmploymentType;
@Repository
public interface JobRepository extends JpaRepository<Job,Long>, JpaSpecificationExecutor<Job> {
	  boolean existsByCompanyIdAndTitleIgnoreCaseAndLocationIgnoreCaseAndEmploymentType(
		        Long companyId,
		        String title,
		        String location,
		        EmploymentType employmentType
		    );
}
