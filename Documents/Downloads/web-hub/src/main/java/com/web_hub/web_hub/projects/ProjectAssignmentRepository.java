package com.web_hub.web_hub.projects;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Long> {

    List<ProjectAssignment> findByProjectId(Long projectId);
    List<ProjectAssignment> findByEmployeeId(Long employeeId);
}
