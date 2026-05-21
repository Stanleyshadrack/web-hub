package com.web_hub.web_hub.hr.issuewarnings.service;
import com.web_hub.web_hub.hr.issuewarnings.api.dto.CreateEmployeeRecordRequest;
import com.web_hub.web_hub.hr.issuewarnings.api.dto.EmployeeRecordResponse;
import com.web_hub.web_hub.hr.issuewarnings.model.EmployeeRecord;
import com.web_hub.web_hub.hr.issuewarnings.repository.EmployeeRecordRepository;
import com.web_hub.web_hub.hr.issuewarnings.RecordType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeRecordService {

    private final EmployeeRecordRepository repository;

    /* ================= CREATE ================= */

    public EmployeeRecordResponse create(CreateEmployeeRecordRequest request) {

        EmployeeRecord record = EmployeeRecord.builder()
                .employeeId(request.getEmployeeId())
                .type(request.getType())
                .title(request.getTitle())
                .description(request.getDescription())
                .severity(request.getSeverity())
                .issuedBy(1L)
                .dateIssued(LocalDate.now())
                .build();

        repository.save(record);

        return mapToResponse(record);
    }

    /* ================= GET ALL ================= */

    public List<EmployeeRecordResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ================= GET BY EMPLOYEE ================= */

    public List<EmployeeRecordResponse> getByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ================= GET WARNINGS ================= */

    public List<EmployeeRecordResponse> getWarnings() {
        return repository.findByType(RecordType.WARNING)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ================= GET COMMENDATIONS ================= */

    public List<EmployeeRecordResponse> getCommendations() {
        return repository.findByType(RecordType.COMMENDATION)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ================= MAPPER ================= */

    private EmployeeRecordResponse mapToResponse(EmployeeRecord record) {
        return EmployeeRecordResponse.builder()
                .id(record.getId())
                .employeeId(record.getEmployeeId())
                .type(record.getType())
                .title(record.getTitle())
                .description(record.getDescription())
                .severity(record.getSeverity())
                .issuedBy(record.getIssuedBy())
                .dateIssued(record.getDateIssued())
                .build();
    }
}
