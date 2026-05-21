package com.web_hub.web_hub.hr.payroll.repository;

import com.web_hub.web_hub.hr.payroll.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByEmployeeId(Long employeeId);
}
