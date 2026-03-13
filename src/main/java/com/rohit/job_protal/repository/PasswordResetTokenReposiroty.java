package com.rohit.job_protal.repository;

import com.rohit.job_protal.entity.PasswordResetToken;
import com.rohit.job_protal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenReposiroty extends JpaRepository<PasswordResetToken, Integer> {
 public Optional<PasswordResetToken> findByToken(String token);
 public Optional<PasswordResetToken> findByUser(User user);

 void deleteAllByUser(User user);
}
