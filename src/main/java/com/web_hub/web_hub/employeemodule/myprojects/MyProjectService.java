package com.web_hub.web_hub.employeemodule.myprojects;

import com.web_hub.web_hub.hr.Employees.model.Employee;
import com.web_hub.web_hub.hr.Employees.repository.EmployeeRepository;
import com.web_hub.web_hub.projects.model.Project;
import com.web_hub.web_hub.projects.model.ProjectAssignment;
import com.web_hub.web_hub.projects.repository.ProjectAssignmentRepository;
import com.web_hub.web_hub.projects.repository.ProjectRepository;
import com.web_hub.web_hub.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyProjectService {

    private final EmployeeRepository employeeRepository;
    private final ProjectAssignmentRepository assignmentRepository;
    private final ProjectRepository projectRepository; // Removed the duplicate declaration here

    public List<Project> getMyProjects(User user) {

        // Step 1: get employee using their email address
        Employee employee = employeeRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Employee profile not found for email: " + user.getEmail()));

        // Step 2: get assignments
        List<ProjectAssignment> assignments =
                assignmentRepository.findByEmployeeId(employee.getId());

        // Step 3: fetch projects using projectId
        return assignments.stream()
                .map(assignment -> projectRepository.findById(assignment.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found")))
                .toList();
    }
}