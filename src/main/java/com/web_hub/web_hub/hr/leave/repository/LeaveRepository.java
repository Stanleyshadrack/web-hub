package com.web_hub.web_hub.hr.leave.repository;

import com.web_hub.web_hub.hr.leave.model.LeaveModel;
import com.web_hub.web_hub.hr.leave.model.LeaveStatus;
import com.web_hub.web_hub.hr.leave.model.LeaveTypeDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveModel, Long> {

    List<LeaveModel> findByStatus(LeaveStatus status);

    List<LeaveModel> findByEmployeeId(Long employeeId);

    List<LeaveModel> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status);

    @Query("SELECT new com.web_hub.web_hub.hr.leave.model.LeaveTypeDays(" +
            "l.type, " +
            "SUM(CASE WHEN l.status = com.web_hub.web_hub.hr.leave.model.LeaveStatus.APPROVED THEN l.numberOfDays ELSE 0 END), " +
            "SUM(CASE WHEN l.status = com.web_hub.web_hub.hr.leave.model.LeaveStatus.PENDING THEN l.numberOfDays ELSE 0 END)) " +
            "FROM LeaveModel l WHERE l.employeeId = :employeeId GROUP BY l.type")
    List<LeaveTypeDays> findBalancesByEmployeeId(@Param("employeeId") Long employeeId);
}