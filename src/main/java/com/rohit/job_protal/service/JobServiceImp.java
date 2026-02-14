package com.rohit.job_protal.service;

import java.util.*;
import java.time.LocalDateTime;

import com.rohit.job_protal.exception.SkillNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rohit.job_protal.dto.response.CompanyDTO;
import com.rohit.job_protal.dto.response.CompanyDetailsDTO;
import com.rohit.job_protal.dto.response.JobDetailsDTO;
import com.rohit.job_protal.dto.response.JobDto;
import com.rohit.job_protal.dto.response.JobListingDTO;
import com.rohit.job_protal.dto.response.SkillResponseDto;
import com.rohit.job_protal.entity.Company;
import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.entity.JobSkill;
import com.rohit.job_protal.entity.JobSkillId;
import com.rohit.job_protal.entity.Skill;
import com.rohit.job_protal.exception.CompanyAlreadyExistException;
import com.rohit.job_protal.exception.DuplicateJobException;
import com.rohit.job_protal.exception.JobNotFound;
import com.rohit.job_protal.filter.JobFilterDto;
import com.rohit.job_protal.filter.JobSpecification;
import com.rohit.job_protal.repository.CompanyRepository;
import com.rohit.job_protal.repository.JobRepository;
import com.rohit.job_protal.repository.JobSkillRepository;
import com.rohit.job_protal.repository.SkillsRepository;

import jakarta.transaction.Transactional;
@Service
public class JobServiceImp implements JobService {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private SkillsRepository skillsRepository;
	@Autowired
	private JobSkillRepository jobSkillRepository;
	@Transactional
	public Job createJob(JobDto jobDto) {
		if (jobRepository.existsByCompanyIdAndTitleIgnoreCaseAndLocationIgnoreCaseAndEmploymentType(
				jobDto.getCompany_id(),
				jobDto.getTitle(),
				jobDto.getLocation(),
				jobDto.getEmploymentType()
		)) {
		    throw new DuplicateJobException(
		        "This job already exists for the selected company and location"
		    );
		}
	
		Company company = companyRepository.findById(jobDto.getCompany_id()).orElseThrow(()->new CompanyAlreadyExistException("Company Not Exist"));
		// create job
		Job job = new Job();
		job.setCompany(company);
		job.setCreatedAt(LocalDateTime.now());
		job.setDescription(jobDto.getDescription());
		job.setEmploymentType(jobDto.getEmploymentType());
		job.setExperienceRequired(jobDto.getExperienceRequired());
		job.setJobStatus(jobDto.getJobStatus());
		job.setLocation(jobDto.getLocation());
		job.setSalaryMax(jobDto.getSalaryMax());
		job.setSalaryMin(jobDto.getSalaryMin());
		job.setTitle(jobDto.getTitle());
		job.setUpdatedAt(LocalDateTime.now());
		jobRepository.save(job);
        for (Long skillId : jobDto.getSkill()) {

            Skill skill = skillsRepository.findById(skillId)
                    .orElseThrow(() -> new SkillNotFound("Skill not found"));

            JobSkill jobSkill = new JobSkill();
            jobSkill.setId(new JobSkillId(job.getId(), skill.getId()));
            jobSkill.setJob(job);
            jobSkill.setSkill(skill);

            jobSkillRepository.save(jobSkill);
        }
		return job;
	}
	
		public List<JobListingDTO> getJob(){	
			List<Job> jobList = jobRepository.findAll();
			List<JobListingDTO> jobListing = new ArrayList<>();
			for(Job job : jobList) {
				JobListingDTO jobListingDTO = new JobListingDTO();
				jobListingDTO.setId(job.getId());
				jobListingDTO.setTitle(job.getTitle());
				jobListingDTO.setEmploymentType(job.getEmploymentType());
				jobListingDTO.setLocation(job.getLocation());
				jobListingDTO.setSalaryMax(job.getSalaryMax());
				jobListingDTO.setSalaryMin(job.getSalaryMin());
				CompanyDTO companyDTO = new CompanyDTO();
				if(companyDTO!=null) {
					companyDTO.setId(job.getCompany().getId());
					companyDTO.setLogoUrl(job.getCompany().getLogo_url());
					companyDTO.setName(job.getCompany().getName());
					jobListingDTO.setCompany(companyDTO);
				}
				List<SkillResponseDto> skills =new ArrayList<>();
				if (job.getJobSkills() != null) {
				    for (JobSkill jobSkill : job.getJobSkills()) {
				        if (jobSkill.getSkill() != null) {
				            SkillResponseDto skillDTO = new SkillResponseDto();
				            skillDTO.setId(jobSkill.getSkill().getId());
				            skillDTO.setName(jobSkill.getSkill().getName());
				            skills.add(skillDTO);
				        }
				    }
				}

				jobListingDTO.setSkills(skills);
				jobListing.add(jobListingDTO);
			}
			
			return jobListing;	
		}

		@Override
		public JobDetailsDTO getJobDetails(Long id) {
			Optional<Job> jobOptional = jobRepository.findById(id);
			JobDetailsDTO jobDetailsDTO = new JobDetailsDTO();
			if(jobOptional.isPresent()) {
				Job job = jobOptional.get();
				jobDetailsDTO.setId(job.getId());
				jobDetailsDTO.setTitle(job.getTitle());
				jobDetailsDTO.setDescription(job.getDescription());
				jobDetailsDTO.setEmploymentType(job.getEmploymentType());
				jobDetailsDTO.setExperienceRequired(job.getExperienceRequired());
				jobDetailsDTO.setLocation(job.getLocation());
				jobDetailsDTO.setSalaryMax(job.getSalaryMax());
				jobDetailsDTO.setSalaryMin(job.getSalaryMin());
				jobDetailsDTO.setStatus(job.getJobStatus());
				CompanyDetailsDTO companyDTO = new CompanyDetailsDTO();
				if(companyDTO!=null) {
					companyDTO.setLogoUrl(job.getCompany().getLogo_url());
					companyDTO.setName(job.getCompany().getName());
					companyDTO.setDescription(job.getCompany().getDescription());
					companyDTO.setWebsiteUrl(job.getCompany().getWebsite_url());
					jobDetailsDTO.setCompany(companyDTO);
				}
				List<SkillResponseDto> skills =new ArrayList<>();
				if (job.getJobSkills() != null) {
				    for (JobSkill jobSkill : job.getJobSkills()) {
				        if (jobSkill.getSkill() != null) {
				            SkillResponseDto skillDTO = new SkillResponseDto();
				            skillDTO.setId(jobSkill.getSkill().getId());
				            skillDTO.setName(jobSkill.getSkill().getName());
				            skills.add(skillDTO);
				        }
				    }
				}

				jobDetailsDTO.setSkills(skills);
			}else {
				throw new JobNotFound("Job Not Found");
			}
			return jobDetailsDTO;
		}
		
@Override
public Page<JobListingDTO> jobSearch(
        JobFilterDto filterDTO,
        Pageable pageable) {

    Page<Job> jobPage =
            jobRepository.findAll(
                    JobSpecification.getSpecification(filterDTO),
                    pageable
            );

    return jobPage.map(job -> {

        JobListingDTO dto = new JobListingDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setLocation(job.getLocation());
        dto.setEmploymentType(job.getEmploymentType());
        dto.setSalaryMin(job.getSalaryMin());
        dto.setSalaryMax(job.getSalaryMax());

        // Company
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(job.getCompany().getId());
        companyDTO.setName(job.getCompany().getName());
        companyDTO.setLogoUrl(job.getCompany().getLogo_url());
        dto.setCompany(companyDTO);

        // Skills
        List<SkillResponseDto> skills = new ArrayList<>();
        for (JobSkill js : job.getJobSkills()) {
            SkillResponseDto s = new SkillResponseDto();
            s.setId(js.getSkill().getId());
            s.setName(js.getSkill().getName());
            skills.add(s);
        }
        dto.setSkills(skills);

        return dto;
    });
}		
}
