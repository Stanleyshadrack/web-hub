package com.web_hub.web_hub.projects.milestone.repository;

import com.web_hub.web_hub.projects.milestone.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    List<Milestone> findByProjectId(Long projectId);
}
