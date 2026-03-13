package com.rohit.job_protal.service;

import java.rmi.AlreadyBoundException;
import java.util.*;
import java.time.LocalDateTime;

import com.rohit.job_protal.dto.request.ApplicationStatusDto;
import com.rohit.job_protal.dto.request.UserResumeDto;
import com.rohit.job_protal.dto.request.UserSkillDto;
import com.rohit.job_protal.dto.response.*;
import com.rohit.job_protal.entity.*;
import com.rohit.job_protal.enums.ApplicationStatus;
import com.rohit.job_protal.exception.*;
import com.rohit.job_protal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rohit.job_protal.filter.JobFilterDto;
import com.rohit.job_protal.filter.JobSpecification;

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

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private JobApplicationRepository jobApplicationRepository;

	@Autowired
	private UserSkillRepository userSkillRepository;

	@Autowired
	private UserEducationRepository userEducationRepository;

	@Autowired
	private UserExperienceRepository userExperienceRepository;

	@Autowired
	private UserResumeRepository userResumeRepository;

	@Autowired
	private EmailService emailService;



	@Autowired
	private UserRepository userRepository;
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

		@Cacheable(value = "jobDetailsCacke",key = "'job'+ #id")
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

	@Cacheable(
			value = "jobSearchCache",
			key = "'search:' + "
					+ "(#filterDTO.search != null ? #filterDTO.search : 'NA') + ':' + "
					+ "(#filterDTO.location != null ? #filterDTO.location : 'NA') + ':' + "
					+ "#pageable.pageNumber + ':' + "
					+ "#pageable.pageSize"
	)
@Override
public PageResponse<JobListingDTO> jobSearch(
        JobFilterDto filterDTO,
        Pageable pageable) {

    Page<Job> jobPage =
            jobRepository.findAll(
                    JobSpecification.getSpecification(filterDTO),
                    pageable
            );

    List<JobListingDTO> jobListingDTOS =  jobPage.map(job -> {

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
    }).getContent();
		return new PageResponse<>(
				jobListingDTOS,
				jobPage.getTotalElements(),
				jobPage.getTotalPages(),
				jobPage.getNumber(),
				jobPage.getSize()
		);
}

	@Override
	public List<AppliedUser> getAppliedUser(Pageable pageable, Long id) {
		Job job =jobRepository.findById(id).orElseThrow(()->new JobNotFound("job not found"));
		Page<JobApplication> applicationPage = jobApplicationRepository.findApplicationsWithUserAndProfile(job,pageable);
		List<AppliedUser> appliedUserList = new ArrayList<>();
		for(JobApplication jobApplication : applicationPage.getContent()){
			UserProfile userProfile = userProfileRepository.findUserProfileByUser(jobApplication.getUser());
			AppliedUser appliedUser = new AppliedUser();
			appliedUser.setAppliedAt(jobApplication.getAppliedAt());
			appliedUser.setName(jobApplication.getUser().getName());
			appliedUser.setCity(userProfile.getCity());
			appliedUser.setEmail(jobApplication.getUser().getEmail());
			appliedUser.setPhone(userProfile.getPhone());
			appliedUser.setUserId(jobApplication.getUser().getId());
			appliedUser.setApplicationId(jobApplication.getId());
			appliedUser.setApplicationStatus(jobApplication.getApplicationStatus());
			appliedUser.setTotalExperience(userProfile.getTotalExperience());

			appliedUserList.add(appliedUser);
		}
		return appliedUserList;
	}


	@Override
	public CandidateResponseDto getCandidateDetails(Long applicationId) {

		JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new NotFoundException("Applied user details not found"));

		User user = userRepository.findById(jobApplication.getUser().getId())
				.orElseThrow(() -> new NotFoundException("User Not found"));

		UserProfile userProfile = userProfileRepository.findUserProfileByUser(user);

		List<UserSkill> userSkills = userSkillRepository.findUserSkillByUserProfile(userProfile);
		List<UserEducation> userEducations = userEducationRepository.findAllByUserProfile(userProfile);
		List<UserExperience> userExperiences = userExperienceRepository.findAllByUserProfile(userProfile);
		UserResume userResume = userResumeRepository.findByUserProfileAndActiveTrue(userProfile);

		CandidateResponseDto candidateResponseDto = new CandidateResponseDto();

		// basic info
		candidateResponseDto.setUserId(user.getId());
		candidateResponseDto.setApplicationId(jobApplication.getId());
		candidateResponseDto.setName(user.getName());
		candidateResponseDto.setEmail(user.getEmail());
		candidateResponseDto.setAppliedAt(jobApplication.getAppliedAt());
		candidateResponseDto.setApplicationStatus(jobApplication.getApplicationStatus());

		// profile mapping
		UserProfileResponseDto profileDto = new UserProfileResponseDto();
		profileDto.setId(userProfile.getId());
		profileDto.setGender(userProfile.getGender());
		profileDto.setPhone(userProfile.getPhone());
		profileDto.setPhoneVerified(userProfile.isPhoneVerified());
		profileDto.setCity(userProfile.getCity());
		profileDto.setState(userProfile.getState());
		profileDto.setCountry(userProfile.getCountry());
		profileDto.setTotalExperience(userProfile.getTotalExperience());

		candidateResponseDto.setProfile(profileDto);

		// skill mapping
		List<UserSkillDtoReponse> skillDtos = userSkills.stream().map(skill -> {
			UserSkillDtoReponse dto = new UserSkillDtoReponse();
			dto.setId(skill.getSkill().getId());
			dto.setName(skill.getSkill().getName());
			dto.setExperience(skill.getExperience());
			dto.setProficiency(skill.getProficiency());
			return dto;
		}).toList();

		candidateResponseDto.setSkills(skillDtos);

		// education mapping
		List<UserEducationResponseDto> educationDtos = userEducations.stream().map(edu -> {
			UserEducationResponseDto dto = new UserEducationResponseDto();
			dto.setId(edu.getId());
			dto.setDegree(edu.getDegree());
			dto.setPercentage(edu.getPercentage());
			dto.setInstitute(edu.getInstitute());
			dto.setStartDate(edu.getStartDate());
			dto.setEndDate(edu.getEndData());
			dto.setEducationStatus(edu.getEducationStatus());
			return dto;
		}).toList();

		candidateResponseDto.setEducation(educationDtos);

		// experience mapping
		List<UserExperienceResponseDto> experienceDtos = userExperiences.stream().map(exp -> {
			UserExperienceResponseDto dto = new UserExperienceResponseDto();
			dto.setId(exp.getId());
			dto.setCompanyName(exp.getCompanyName());
			dto.setRole(exp.getRole());
			dto.setEmploymentType(exp.getEmploymentType());
			dto.setEmploymentStatus(exp.getEmploymentStatus());
			dto.setStartDate(exp.getStartDate());
			dto.setEndDate(exp.getEndDate());
			dto.setDescription(exp.getDescription());
			return dto;
		}).toList();

		candidateResponseDto.setExperience(experienceDtos);

		// resume mapping
		if (userResume != null) {
			UserResumeDto resumeDto = new UserResumeDto();
			resumeDto.setFileName(userResume.getFileName());
			resumeDto.setFileUrl(userResume.getFileUrl());
			candidateResponseDto.setResume(resumeDto);		}

		return candidateResponseDto;
	}

	@Override
	public HashMap<String, Object> updateApplicationStatus(Long applicationId, ApplicationStatusDto applicationStatusDto) {
		JobApplication jobApplication = jobApplicationRepository.findById(applicationId).orElseThrow(()->new NotFoundException("Application not found"));
		if(jobApplication.getApplicationStatus() == ApplicationStatus.HIRED){
			throw new ApplicationStatusException("Status cannot be changed after hiring");
		}
		if(jobApplication.getApplicationStatus() == applicationStatusDto.getApplicationStatus()){
			throw new ApplicationStatusException("Application already in this status");
		}

		jobApplication.setApplicationStatus(applicationStatusDto.getApplicationStatus());
		jobApplicationRepository.save(jobApplication);
		emailService.sendApplicactionStatusChange(jobApplication.getId());

		HashMap<String,Object> map = new HashMap<>();
		map.put("applicationId",jobApplication.getId());
		map.put("status",jobApplication.getApplicationStatus());
		return map;
	}
}
