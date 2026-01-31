package com.rohit.job_protal.filter;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.rohit.job_protal.entity.Job;
import com.rohit.job_protal.entity.JobSkill;
import com.rohit.job_protal.entity.Skill;

public class JobSpecification {

    public static Specification<Job> getSpecification(JobFilterDto dto) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            /* ================= SKILL FILTER ================= */
            if (!CollectionUtils.isEmpty(dto.getSkill())) {

                Join<Job, JobSkill> jobSkillJoin =
                        root.join("jobSkills", JoinType.INNER);

                Join<JobSkill, Skill> skillJoin =
                        jobSkillJoin.join("skill", JoinType.INNER);

                predicates.add(
                        skillJoin.get("id").in(dto.getSkill())
                );

                query.distinct(true);
            }

            /* ================= EMPLOYMENT TYPE ================= */
            if (!CollectionUtils.isEmpty(dto.getEmploymentType())) {
                predicates.add(
                        root.get("employmentType")
                                .in(dto.getEmploymentType())
                );
            }

            /* ================= SALARY FILTER ================= */
            if (dto.getMin_salary() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("salaryMin"),
                                dto.getMin_salary()
                        )
                );
            }

            if (dto.getMax_salary() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("salaryMax"),
                                dto.getMax_salary()
                        )
                );
            }

            /* ================= LOCATION ================= */
            if (StringUtils.hasText(dto.getLocation())) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("location")),
                                "%" + dto.getLocation().toLowerCase() + "%"
                        )
                );
            }

            /* ================= SEARCH ================= */
            if (StringUtils.hasText(dto.getSearch())) {

                String keyword = "%" + dto.getSearch().toLowerCase() + "%";

                Predicate title =
                        cb.like(cb.lower(root.get("title")), keyword);

                Predicate description =
                        cb.like(cb.lower(root.get("description")), keyword);

                predicates.add(cb.or(title, description));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
