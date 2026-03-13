package com.rohit.job_protal.service;

import com.rohit.job_protal.dto.response.AppliedJobResponseDTO;
import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.entity.JobApplication;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.enums.ApplicationStatus;
import com.rohit.job_protal.exception.*;
import com.rohit.job_protal.repository.*;
import com.rohit.job_protal.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobApplicationServiceImp implements JobApplicationService{

    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserResumeRepository userResumeRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Override
    public JobApplication updateJobApplicationStatus(Long jobId) {
        checkEligbileCriteria();
        User user = securityUtil.getCurrentUser();
        Job job = jobRepository.findById(jobId).orElseThrow(()->new JobNotFound("Job not found"));
        if(jobApplicationRepository.existsJobApplicationByUserAndJob(user,job)){
            throw new JobAlreadyApplied("Job Already Applied");
        }
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUser(user);
        jobApplication.setJob(job);
        jobApplication.setAppliedAt(LocalDateTime.now());
        jobApplication.setApplicationStatus(ApplicationStatus.APPLIED);
        return jobApplicationRepository.save(jobApplication);

    }

    @Override
    public List<AppliedJobResponseDTO> getUserAppliedJob() {
        User user = securityUtil.getCurrentUser();
        List<JobApplication> appliedJob = jobApplicationRepository.findAllByUser(user);
        List<AppliedJobResponseDTO> dtos = new ArrayList<>();
        for(JobApplication jobApplication : appliedJob){
            AppliedJobResponseDTO dto = new AppliedJobResponseDTO();
            dto.setApplicationId(jobApplication.getId());
            dto.setJobTitle(jobApplication.getJob().getTitle());
            dto.setJobId(jobApplication.getJob().getId());
            dto.setAppliedAt(jobApplication.getAppliedAt());
            dto.setApplicationStatus(jobApplication.getApplicationStatus());
            dto.setCompanyName(jobApplication.getJob().getCompany().getName());
            dto.setEmploymentType(jobApplication.getJob().getEmploymentType());
            dto.setLocation(jobApplication.getJob().getLocation());
            dto.setSalaryMax(jobApplication.getJob().getSalaryMax());
            dto.setSalaryMin(jobApplication.getJob().getSalaryMin());
            dto.setCompanyLogo(jobApplication.getJob().getCompany().getLogo_url());
            dtos.add(dto);
        }
        return dtos;
    }

    public void checkEligbileCriteria(){
        User user = securityUtil.getCurrentUser();
        UserProfile userProfile = userProfileRepository.findUserProfileByUser(user);
        if(userProfile==null){
            throw new NotFoundException("Userprofile not found");
        }
        if (!userProfile.isPhoneVerified()){
            throw new PhoneNumberNotVerified("Verify Phone Number first");
        }
        if(!user.isEnabled()){
            throw  new EmailNotVerifed("Verify email first");
        }
        if (!userResumeRepository.existsByUserProfileAndActiveTrue(userProfile)){
            throw new UploadResume("Upload resume first");
        }
        if(!userSkillRepository.existsUserSkillByUserProfile(userProfile)){
            throw new SkillNotPresent("Add atleast one skill");
        }
    }
}
