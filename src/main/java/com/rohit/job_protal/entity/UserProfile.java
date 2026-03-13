package com.rohit.job_protal.entity;
import java.lang.invoke.StringConcatFactory;
import java.security.PrivateKey;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.type.TrueFalseConverter;

import com.rohit.job_protal.enums.Gender;
import com.rohit.job_protal.enums.ProfileStatus;
import org.hibernate.validator.constraints.UniqueElements;

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
	private boolean phoneVerified;
	private String city;
	private String state;
	private String country;
	private Integer totalExperience;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
