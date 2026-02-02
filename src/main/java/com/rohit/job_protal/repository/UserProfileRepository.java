package com.rohit.job_protal.repository;

import com.rohit.job_protal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.job_protal.entity.UserProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    public UserProfile findByUser(User user);
    boolean existsByUser(User user);
}
