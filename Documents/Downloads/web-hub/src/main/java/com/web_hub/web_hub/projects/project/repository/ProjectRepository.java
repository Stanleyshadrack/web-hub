package com.web_hub.web_hub.projects.project.repository;

import com.web_hub.web_hub.projects.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
