package com.web_hub.web_hub.departments.repository;

import com.web_hub.web_hub.departments.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);

    @Query("SELECT d, COUNT(e.id) FROM Department d LEFT JOIN Employee e ON d.id = e.departmentId GROUP BY d.id")
    List<Object[]> findAllDepartmentsWithHeadcount();
}
