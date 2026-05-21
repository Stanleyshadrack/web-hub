package com.web_hub.web_hub.hr.issuewarnings.repository;

import com.web_hub.web_hub.hr.issuewarnings.model.EmployeeRecord;
import com.web_hub.web_hub.hr.issuewarnings.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, Long> {

    List<EmployeeRecord> findByEmployeeId(Long employeeId);
    List<EmployeeRecord> findByType(RecordType type);
}
