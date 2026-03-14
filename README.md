# Job Portal Backend

A scalable **Job Portal Backend System** built using **Spring Boot** that simulates a real-world recruitment platform.
The system allows candidates to create professional profiles, apply for jobs, and track application status, while administrators can manage job postings and review applicants through an Applicant Tracking System (ATS).

This project focuses on **clean backend architecture, secure authentication, performance optimization, and real hiring workflow simulation**.

---

# Project Overview

The backend provides REST APIs for managing job postings, candidate profiles, and job applications.
It implements a structured workflow where candidates must complete profile requirements before applying for jobs, ensuring that recruiters receive **verified and meaningful applications**.

The system includes:

• Candidate profile management
• Job application workflow
• Admin applicant management
• Application status tracking
• Email notifications for hiring updates
• Redis caching for performance improvement

---

# Key Features

## Authentication & Security

* JWT-based authentication using Spring Security
* Role-based access (Admin / Candidate)
* Email verification for real user validation
* Phone number verification
* Secure API access using authorization tokens

---

## Candidate Profile System

Candidates can create a professional profile including:

* Personal profile details
* Skills with experience and proficiency
* Educational qualifications
* Work experience
* Resume upload

Before applying to a job the system validates:

* Email must be verified
* Phone number must be verified
* Resume must be uploaded
* At least one skill must be added

This ensures recruiters receive **complete candidate profiles**.

---

## Job Application Workflow

Candidates can:

* Browse job listings
* View job details
* Apply for jobs
* Track their application status

Application status lifecycle:

APPLIED → SHORTLISTED → HIRED
APPLIED → REJECTED

Each status change triggers an **email notification to the candidate**.

---

## Admin Features

Administrators can:

* View all applicants for a specific job
* Access full candidate profiles
* Review skills, education, experience, and resume
* Update application status
* Manage recruitment workflow

Admin dashboard APIs support **pagination for handling large applicant lists**.

---

## Email Notification System

Automated emails are sent when the application status changes:

* Shortlisted notification
* Rejection notification
* Hiring confirmation

This simulates a **real recruitment communication process**.

---

## Redis Caching (Performance Optimization)

Redis is integrated to cache frequently accessed data such as:

* Job listings
* Job details

Benefits:

* Faster API responses
* Reduced database load
* Better scalability for high traffic systems

---

# Tech Stack

Backend Framework
Spring Boot

Security
Spring Security
JWT Authentication

Database
MySQL

ORM
Spring Data JPA / Hibernate

Caching
Redis

Email Service
JavaMailSender

Build Tool
Maven

---

# Architecture

The project follows a **layered architecture** for better maintainability and scalability.

Controller Layer
Handles HTTP requests and responses.

Service Layer
Contains business logic and validation.

Repository Layer
Handles database operations using JPA.

Entity Layer
Represents database tables.

DTO Layer
Used to separate API responses from database entities.

---

# API Capabilities

Authentication APIs

* Register user
* Login user
* JWT authentication

Candidate APIs

* Create and update profile
* Add skills
* Add education
* Add experience
* Upload resume
* Apply for jobs
* View applied jobs

Admin APIs

* View applicants for a job
* View candidate details
* Update application status
* Manage recruitment workflow

---

# Real World Concepts Implemented

This project demonstrates several backend engineering concepts:

* RESTful API design
* Secure authentication with JWT
* Role-based authorization
* Data validation before critical actions
* Applicant Tracking System (ATS) workflow
* Redis caching for performance optimization
* Email notification system
* Pagination for scalable data retrieval
* Clean layered architecture

---

# Future Improvements

Possible enhancements for production-level systems:

* Job search and filtering
* Saved jobs feature
* Notification center inside the platform
* Admin analytics dashboard
* Resume parsing
* Interview scheduling

---

# Author

Rohit Pillewan
Full Stack Developer (Java + React)

This project was built as part of a **full stack job portal system**, focusing on backend architecture, secure APIs, and scalable design.
