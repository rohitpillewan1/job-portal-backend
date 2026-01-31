package com.rohit.job_protal.repository;

import org.apache.el.parser.BooleanNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.job_protal.entity.Skill;
@Repository
public interface SkillsRepository extends JpaRepository<Skill,Long> {
	public boolean existsByNameIgnoreCase(String name);
}
