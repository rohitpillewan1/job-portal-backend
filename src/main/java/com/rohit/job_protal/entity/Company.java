package com.rohit.job_protal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "companies")
@Data
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false,unique = true)
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	private String website_url;
	private String logo_url;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
