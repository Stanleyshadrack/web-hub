package com.web_hub.web_hub.hr.leave.service;

import com.web_hub.web_hub.constants.Constants;
import com.web_hub.web_hub.hr.leave.api.dto.LeaveRequest;
import com.web_hub.web_hub.hr.leave.api.dto.LeaveResponse;
import com.web_hub.web_hub.hr.leave.api.dto.LeaveTrackerResponse;
import com.web_hub.web_hub.hr.leave.model.LeaveModel;
import com.web_hub.web_hub.hr.leave.model.LeaveStatus;
import com.web_hub.web_hub.hr.leave.model.LeaveTypeDays;
import com.web_hub.web_hub.hr.leave.repository.LeaveRepository;
import com.web_hub.web_hub.hr.leave.model.LeaveType;
import com.web_hub.web_hub.user.model.User;
import com.web_hub.web_hub.user.repository.UserRepository;
import com.web_hub.web_hub.user.service.UserProfileService;
import com.web_hub.web_hub.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository repository;
    private final UserProfileService userProfileService;
    private final UserRepository userRepository;

    // Single source of truth for leave allocations pulling from your Constants file
    private static final Map<LeaveType, Integer> LEAVE_ALLOCATIONS = Map.of(
            LeaveType.CASUAL_LEAVE, Constants.MAX_ANNUAL_LEAVE_DAYS,
            LeaveType.SICK_LEAVE, Constants.MAX_SICK_LEAVE_DAYS,
            LeaveType.EARNED_LEAVE, Constants.MAX_EARNED_LEAVE_DAYS,
            LeaveType.COMPASSIONATE_LEAVE, Constants.MAX_COMPASSIONATE_LEAVE_DAYS,
            LeaveType.MATERNITY_LEAVE, Constants.MAX_MATERNITY_LEAVE_DAYS,
            LeaveType.PATERNITY_LEAVE, Constants.MAX_PATERNITY_LEAVE_DAYS
    );

    /* ================= EMPLOYEE: APPLY LEAVE ================= */
    public LeaveResponse apply(LeaveRequest req) {
        User currentUser = userProfileService.getCurrentUser();

        if (currentUser.getEmployee() == null) {
            throw new ResourceNotFoundException("No active Employee profile linked to user account: " + currentUser.getUsername());
        }

        Long employeeId = currentUser.getEmployee().getId();

        int requestedDays = 0;
        if (req.getStartDate() != null && req.getEndDate() != null) {
            requestedDays = (int) ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate()) + 1;
        }

        validateLeaveDaysLimit(employeeId, req.getType(), requestedDays, null);

        LeaveModel leave = new LeaveModel();
        leave.setUserId(currentUser.getId());
        leave.setEmployeeId(employeeId);
        leave.setType(req.getType());
        leave.setStartDate(req.getStartDate());
        leave.setEndDate(req.getEndDate());
        leave.setReason(req.getReason());
        leave.setNumberOfDays(requestedDays);
        leave.setStatus(LeaveStatus.PENDING);
        leave.setCreatedAt(LocalDateTime.now());

        repository.save(leave);
        return map(leave, currentUser);
    }

    /* ================= UPDATE LEAVE INFO ================= */
    public LeaveResponse updateLeave(Long id, LeaveRequest req) {
        User currentUser = userProfileService.getCurrentUser();

        if (currentUser.getEmployee() == null) {
            throw new ResourceNotFoundException("No active Employee profile linked to user account: " + currentUser.getUsername());
        }

        LeaveModel leave = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));

        if (!leave.getUserId().equals(currentUser.getId())) {
            throw new IllegalStateException("You are not authorized to update this leave request.");
        }

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new IllegalStateException("Cannot update a leave request that has already been " + leave.getStatus());
        }

        int requestedDays = 0;
        if (req.getStartDate() != null && req.getEndDate() != null) {
            requestedDays = (int) ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate()) + 1;
        }

        validateLeaveDaysLimit(currentUser.getEmployee().getId(), req.getType(), requestedDays, id);

        leave.setType(req.getType());
        leave.setStartDate(req.getStartDate());
        leave.setEndDate(req.getEndDate());
        leave.setReason(req.getReason());
        leave.setNumberOfDays(requestedDays);

        repository.save(leave);
        return map(leave, currentUser);
    }

    /* ================= LEAVE TRACKER (BALANCES) ================= */
    public List<LeaveTrackerResponse> getMyLeaveBalances() {
        User currentUser = userProfileService.getCurrentUser();

        if (currentUser.getEmployee() == null) {
            return Collections.emptyList();
        }

        Long employeeId = currentUser.getEmployee().getId();
        List<LeaveTypeDays> rawBalances = repository.findBalancesByEmployeeId(employeeId);

        return rawBalances.stream()
                .map(dbRow -> {
                    int totalAllocated = getAllocatedDays(dbRow.leaveType());
                    int daysUsed = dbRow.approvedDays() != null ? dbRow.approvedDays().intValue() : 0;
                    int daysRemaining = totalAllocated - daysUsed;
                    String displayName = formatLeaveTypeName(dbRow.leaveType());

                    return new LeaveTrackerResponse(
                            dbRow.leaveType(),
                            displayName,
                            totalAllocated,
                            daysUsed,
                            daysRemaining
                    );
                })
                .toList();
    }

    /* ================= MY LEAVES ================= */
    public List<LeaveResponse> getMyLeaves() {
        User currentUser = userProfileService.getCurrentUser();
        Long employeeId = (currentUser.getEmployee() != null) ? currentUser.getEmployee().getId() : null;

        if (employeeId == null) return List.of();

        return repository.findByEmployeeId(employeeId)
                .stream()
                .map(l -> map(l, currentUser))
                .toList();
    }

    /* ================= GET ALL ================= */
    public List<LeaveResponse> getAllLeaves() {
        return repository.findAll()
                .stream()
                .map(l -> {
                    User requester = userRepository.findById(l.getUserId()).orElse(null);
                    return map(l, requester);
                })
                .toList();
    }

    /* ================= APPROVE / REJECT ================= */
    public LeaveResponse approveLeave(Long id) {
        return processLeaveDecision(id, LeaveStatus.APPROVED);
    }

    public LeaveResponse rejectLeave(Long id) {
        return processLeaveDecision(id, LeaveStatus.REJECTED);
    }

    private LeaveResponse processLeaveDecision(Long id, LeaveStatus status) {
        User manager = userProfileService.getCurrentUser();

        LeaveModel leave = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));

        leave.setStatus(status);
        leave.setDecisionDate(LocalDateTime.now());
        leave.setApproverId(manager.getId());

        repository.save(leave);

        User requester = userRepository.findById(leave.getUserId()).orElse(null);
        return map(leave, requester);
    }

    /* ================= GET BY ID ================= */
    public LeaveResponse getById(Long id) {
        LeaveModel leave = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));
        User requester = userRepository.findById(leave.getUserId()).orElse(null);
        return map(leave, requester);
    }

    /* ================= HELPERS & VALIDATIONS ================= */
    private void validateLeaveDaysLimit(Long employeeId, LeaveType type, int requestedDays, Long excludeLeaveId) {
        if (employeeId == null) return;

        List<LeaveModel> existingLeaves = repository.findByEmployeeId(employeeId);

        int daysAlreadyUsedOrPending = existingLeaves.stream()
                .filter(l -> l.getType() == type)
                .filter(l -> excludeLeaveId == null || !l.getId().equals(excludeLeaveId))
                .filter(l -> l.getStatus() == LeaveStatus.APPROVED || l.getStatus() == LeaveStatus.PENDING)
                .mapToInt(LeaveModel::getNumberOfDays)
                .sum();

        int maxAllowedDays = getAllocatedDays(type);

        if ((daysAlreadyUsedOrPending + requestedDays) > maxAllowedDays) {
            int remainingAvailable = maxAllowedDays - daysAlreadyUsedOrPending;
            throw new IllegalStateException(String.format(
                    "Leave allocation exceeded for %s. You requested %d days, but you only have %d days remaining.",
                    formatLeaveTypeName(type), requestedDays, Math.max(0, remainingAvailable)
            ));
        }
    }

    // DRY: Pulls securely from the master map allocation rules config block
    private int getAllocatedDays(LeaveType type) {
        return LEAVE_ALLOCATIONS.getOrDefault(type, Constants.DEFAULT_MAX_LEAVE_DAYS);
    }

    /**
     * Elegant cleaner formatting for type labels (e.g., CASUAL_LEAVE -> Casual Leave)
     */
    private String formatLeaveTypeName(LeaveType type) {
        if (type == null) return "";
        String[] words = type.name().split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }
        return result.toString().trim();
    }

    private LeaveResponse map(LeaveModel l, User requester) {
        String refId = String.format("LR%03d", l.getId() != null ? l.getId() : 0);
        String empName = (requester != null) ? requester.getUsername() : "Unknown User";

        String appName = null;
        if (l.getApproverId() != null) {
            User approver = userRepository.findById(l.getApproverId()).orElse(null);
            if (approver != null) {
                appName = approver.getUsername();
            }
        }

        return new LeaveResponse(
                l.getId(),
                refId,
                empName,
                l.getType(),
                l.getStartDate(),
                l.getEndDate(),
                l.getNumberOfDays(),
                l.getStatus(),
                l.getReason(),
                appName,
                l.getDecisionDate(),
                l.getCreatedAt()
        );
    }
}