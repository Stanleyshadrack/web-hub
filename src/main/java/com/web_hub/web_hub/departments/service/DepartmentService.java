package com.web_hub.web_hub.departments.service;

import com.web_hub.web_hub.departments.api.dto.DepartmentRequest;
import com.web_hub.web_hub.departments.api.dto.DepartmentResponse;
import com.web_hub.web_hub.departments.model.Department;
import com.web_hub.web_hub.departments.repository.DepartmentRepository;
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

    /* ================= CREATE ================= */
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setId(generateNextId());
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setHeadOfDepartment(request.getHeadOfDepartment());
        department.setCreatedAt(LocalDateTime.now());

        Department saved = departmentRepository.save(department);
        return mapToResponse(saved, "0", "0");
    }

    /* ================= UPDATE ================= */
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        dept.setName(request.getName());
        dept.setDescription(request.getDescription());
        dept.setHeadOfDepartment(request.getHeadOfDepartment());

        Department updated = departmentRepository.save(dept);
        String headCount = String.valueOf(employeeRepository.countByDepartmentId(String.valueOf(id)));
        return mapToResponse(updated, headCount, "0");
    }

    /* ================= ARCHIVE (Delete) ================= */
    public void archiveDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found");
        }
        departmentRepository.deleteById(id);
    }

    /* ================= GET BY ID ================= */
    public DepartmentResponse getDepartmentById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        String headCount = String.valueOf(employeeRepository.countByDepartmentId(String.valueOf(id)));
        return mapToResponse(dept, headCount, "0");
    }

    /* ================= GET ALL ================= */
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

    private DepartmentResponse mapToResponse(Department dept, String headCount, String budget) {
        return DepartmentResponse.builder()
                .id(dept.getId())
                .name(dept.getName())
                .description(dept.getDescription())
                .headCount(headCount)
                .annualBudget(budget)
                .build();
    }
}