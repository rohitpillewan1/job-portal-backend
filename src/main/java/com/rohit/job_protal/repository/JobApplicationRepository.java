package com.rohit.job_protal.repository;

import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.entity.JobApplication;
import com.rohit.job_protal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
    Boolean existsJobApplicationByUserAndJob(User user, Job job);

    List<JobApplication> findAllByUser(User user);
    Page<JobApplication>  findAllByJob(Job job, Pageable pageable);
        @Query(
                value = """
    SELECT ja
    FROM JobApplication ja
    JOIN FETCH ja.user u
    JOIN UserProfile up ON up.user = u
    WHERE ja.job = :job
    """,
                countQuery = """
    SELECT COUNT(ja)
    FROM JobApplication ja
    WHERE ja.job = :job
    """
        )
    Page<JobApplication> findApplicationsWithUserAndProfile(Job job, Pageable pageable);
}
