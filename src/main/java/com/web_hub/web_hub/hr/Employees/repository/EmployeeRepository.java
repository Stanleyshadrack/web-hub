package com.web_hub.web_hub.hr.Employees.repository;

import com.web_hub.web_hub.hr.Employees.model.Employee;
import com.web_hub.web_hub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    long countByStatus(String status);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUser(User user);
    boolean existsByUser(User user);

    // Use an explicit JPQL query to avoid "PropertyReferenceException"
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.departmentId = :deptId")
    long countByDepartmentId(@Param("deptId") String departmentId);
}