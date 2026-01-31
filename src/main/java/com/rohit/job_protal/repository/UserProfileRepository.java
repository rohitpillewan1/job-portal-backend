package com.rohit.job_protal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.job_protal.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {

}
