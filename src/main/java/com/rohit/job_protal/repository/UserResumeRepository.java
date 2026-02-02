package com.rohit.job_protal.repository;

import com.rohit.job_protal.entity.UserResume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResumeRepository extends JpaRepository<UserResume,Long> {
}
