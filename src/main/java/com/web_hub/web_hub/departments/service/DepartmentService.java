package com.web_hub.web_hub.departments.service;

import com.web_hub.web_hub.departments.api.dto.DepartmentRequest;
import com.web_hub.web_hub.departments.api.dto.DepartmentResponse;
import com.web_hub.web_hub.departments.model.Department;
import com.web_hub.web_hub.departments.repository.DepartmentRepository;
import com.web_hub.web_hub.exception.AuthException;
import com.web_hub.web_hub.exception.ResourceNotFoundException;
import com.web_hub.web_hub.hr.Employees.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new AuthException("Department name cannot be empty");
        }

        if (departmentRepository.existsByName(request.getName())) {
            throw new AuthException("Department with this name already exists");
        }

        Department department = new Department();
        department.setId(generateNextId());
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setHeadOfDepartment(request.getHeadOfDepartment());
        department.setCreatedAt(LocalDateTime.now());

        return mapToResponse(departmentRepository.save(department), "0", "0");
    }

    public DepartmentResponse updateDepartment(String id, DepartmentRequest request) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));

        dept.setName(request.getName());
        dept.setDescription(request.getDescription());
        dept.setHeadOfDepartment(request.getHeadOfDepartment());

        Department updated = departmentRepository.save(dept);
        String headCount = String.valueOf(employeeRepository.countByDepartmentId(id));
        return mapToResponse(updated, headCount, "0");
    }

    public void archiveDepartment(String id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot archive: Department not found with ID: " + id);
        }
        departmentRepository.deleteById(id);
    }

    public DepartmentResponse getDepartmentById(String id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));

        String headCount = String.valueOf(employeeRepository.countByDepartmentId(id));
        return mapToResponse(dept, headCount, "0");
    }

    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(dept -> mapToResponse(dept,
                        String.valueOf(employeeRepository.countByDepartmentId(dept.getId())),
                        "0"))
                .collect(Collectors.toList());
    }

    private String generateNextId() {
        return "D" + (departmentRepository.count() + 1);
    }

    // Updated to pull headOfDepartment straight from the dept model
    private DepartmentResponse mapToResponse(Department dept, String headCount, String budget) {
        return DepartmentResponse.builder()
                .id(dept.getId())
                .name(dept.getName())
                .description(dept.getDescription())
                .headCount(headCount)
                .annualBudget(budget)
                .headOfDepartment(dept.getHeadOfDepartment()) // Fixed mapping here
                .build();
    }
}