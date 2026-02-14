package com.rohit.job_protal.service;
import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.entity.UserProfile;
import com.rohit.job_protal.entity.UserResume;
import com.rohit.job_protal.repository.*;
import com.rohit.job_protal.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileStatusServiceImp implements ProfileStatusService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserResumeRepository userResumeRepository;
    @Autowired
    private UserEducationRepository userEducationRepository;
    @Autowired
    private UserExperienceRepository userExperienceRepository;
    @Autowired
    private UserSkillRepository userSkillRepository;
    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public Long getProfileStatusPercenatage() {

        User user = securityUtil.getCurrentUser();
        UserProfile profile = userProfileRepository.findUserProfileByUser(user);

        if (profile == null) {
            return 0L;
        }

        int totalSections = 5;
        int completedSections = 0;

        // 1️⃣ Basic Info
        boolean basicProfile =
                profile.getCity() != null &&
                        profile.getState() != null &&
                        profile.getPhone() != null &&
                        profile.getGender() != null &&
                        profile.getTotalExperience() != null &&
                        profile.getCountry() != null;

        if (basicProfile) completedSections++;

        // 2️⃣ Resume
        UserResume resume = userResumeRepository.findUserResumeByUserProfile(profile);
        boolean resumeCheck =
                resume != null &&
                        resume.getFileName() != null &&
                        resume.getFileUrl() != null;

        if (resumeCheck) completedSections++;

        // 3️⃣ Skills (at least one)
        boolean skillCheck = userSkillRepository.existsUserSkillByUserProfile(profile);
        if (skillCheck) completedSections++;

        // 4️⃣ Education (at least one)
        boolean educationCheck = userEducationRepository.existsUserEducationByUserProfile(profile);
        if (educationCheck) completedSections++;

        // 5️⃣ Experience (Conditional)
        boolean experienceCheck = true;

        if (profile.getTotalExperience() != null && profile.getTotalExperience() > 0) {
            experienceCheck = userExperienceRepository.existsUserExperienceByUserProfile(profile);
        }

        if (experienceCheck) completedSections++;

        // Percentage Calculation
        long percentage = (completedSections * 100L) / totalSections;

        return percentage;
    }
}

