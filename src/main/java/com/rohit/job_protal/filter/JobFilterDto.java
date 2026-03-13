package com.rohit.job_protal.filter;


import java.io.Serializable;
import java.util.List;

import com.rohit.job_protal.enums.EmploymentType;

import lombok.Data;

@Data
public class JobFilterDto implements Serializable {
	private List<EmploymentType> employmentType;
	private String search;
	private Integer max_salary;
	private Integer min_salary;
	private String location;
	private List<Long> skill;
}
