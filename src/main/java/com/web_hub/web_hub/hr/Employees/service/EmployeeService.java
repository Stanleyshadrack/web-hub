package com.web_hub.web_hub.hr.Employees.service;

import com.web_hub.web_hub.admin.auditlog.AuditLogService;
import com.web_hub.web_hub.departments.model.Department;
import com.web_hub.web_hub.departments.repository.DepartmentRepository;
import com.web_hub.web_hub.hr.Employees.api.dto.CreateEmployeeRequest;
import com.web_hub.web_hub.hr.Employees.model.Employee;
import com.web_hub.web_hub.hr.Employees.repository.EmployeeRepository;
import com.web_hub.web_hub.hr.Employees.api.dto.EmployeeResponse;
import com.web_hub.web_hub.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AuditLogService auditLogService;

    /* ================= CREATE ================= */
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        // Validate uniqueness using Email instead of User
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("An employee with this email already exists");
        }

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setJobTitle(request.getJobTitle());
        employee.setDepartment(request.getDepartment());
        employee.setDepartmentId(request.getDepartmentId());

        // Onboarding / HR fields
        employee.setStartDate(request.getStartDate());
        employee.setIdentificationNumber(request.getIdentificationNumber());
        employee.setIdType(request.getIdType());
        employee.setNationality(request.getNationality());

        // Financial / KRA fields
        employee.setBankName(request.getBankName());
        employee.setAccountNumber(request.getAccountNumber());
        employee.setKraPin(request.getKraPin());

        employee.setStatus("ACTIVE");
        employee.setCreatedAt(LocalDateTime.now());

        Employee saved = employeeRepository.save(employee);

        auditLogService.logAction(
                "CREATE",
                "Employee",
                saved.getId(),
                saved.getEmail(),
                "HR",
                "Created new employee profile"
        );

        return mapToResponse(saved);
    }

    /* ================= GET ALL ================= */
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /* ================= GET BY ID ================= */
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return mapToResponse(employee);
    }

    /* ================= UPDATE ================= */
    public EmployeeResponse updateEmployee(Long id, CreateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Validate department from database
        Department department = departmentRepository.findByName(request.getDepartment())
                .orElseThrow(() -> new RuntimeException("Invalid or non-existing department"));

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setJobTitle(request.getJobTitle());
        employee.setDepartment(department.getName());
        employee.setDepartmentId(request.getDepartmentId());
        employee.setPhone(request.getPhone());

        // Update Onboarding / HR fields
        employee.setStartDate(request.getStartDate());
        employee.setIdentificationNumber(request.getIdentificationNumber());
        employee.setIdType(request.getIdType());
        employee.setNationality(request.getNationality());

        // Update Financial / KRA fields
        employee.setBankName(request.getBankName());
        employee.setAccountNumber(request.getAccountNumber());
        employee.setKraPin(request.getKraPin());

        Employee updated = employeeRepository.save(employee);

        auditLogService.logAction(
                "UPDATE",
                "Employee",
                updated.getId(),
                updated.getEmail(),
                "HR",
                "Updated employee details"
        );

        return mapToResponse(updated);
    }

    /* ================= DELETE ================= */
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeRepository.delete(employee);

        auditLogService.logAction(
                "DELETE",
                "Employee",
                employee.getId(),
                employee.getEmail(),
                "HR",
                "Deleted employee"
        );
    }

    /* ================= SUSPEND ================= */
    public void suspendEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setStatus("SUSPENDED");
        employeeRepository.save(employee);

        auditLogService.logAction(
                "SUSPEND",
                "Employee",
                employee.getId(),
                employee.getEmail(),
                "HR",
                "Suspended employee account"
        );
    }

    /* ================= GET PROFILE FROM USER ================= */
    public EmployeeResponse getMyProfile(User user) {
        // Updated to use email lookup to align with the decoupled entity architecture
        Employee employee = employeeRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("No employee profile found for email: " + user.getEmail()));

        return mapToResponse(employee);
    }

    /* ================= MAPPER ================= */
    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .jobTitle(employee.getJobTitle())
                .department(employee.getDepartment())
                .departmentId(employee.getDepartmentId())
                .status(employee.getStatus())


                // Onboarding / HR fields
                .startDate(employee.getStartDate())
                .identificationNumber(employee.getIdentificationNumber())
                .idType(employee.getIdType())
                .nationality(employee.getNationality())

                // Financial / KRA fields
                .bankName(employee.getBankName())
                .accountNumber(employee.getAccountNumber())
                .kraPin(employee.getKraPin())

                .createdAt(employee.getCreatedAt())
                .build();
    }
}