package com.web_hub.web_hub.timesheets.repository;

import com.web_hub.web_hub.timesheets.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    List<Timesheet> findByProjectId(Long projectId);

    List<Timesheet> findByEmployeeId(Long employeeId);
}
