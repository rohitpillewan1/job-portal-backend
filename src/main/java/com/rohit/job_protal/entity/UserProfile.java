package com.rohit.job_protal.entity;
import java.lang.invoke.StringConcatFactory;
import java.security.PrivateKey;
import java.time.LocalDateTime;

import lombok.Data;
import org.hibernate.type.TrueFalseConverter;

import com.rohit.job_protal.enums.Gender;
import com.rohit.job_protal.enums.ProfileStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Data
@Entity
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	@OneToOne
	@JoinColumn(name = "user_id",nullable = false,unique = true)
	private User user;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String phone;
	private String city;
	private String state;
	private String country;
	private Integer totalExperience;
	@Enumerated(EnumType.STRING)
	private ProfileStatus profileStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
