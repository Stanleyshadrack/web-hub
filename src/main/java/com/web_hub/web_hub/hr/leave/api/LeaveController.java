package com.web_hub.web_hub.hr.leave.api;

import com.web_hub.web_hub.hr.leave.api.dto.LeaveRequest;
import com.web_hub.web_hub.hr.leave.api.dto.LeaveResponse;
import com.web_hub.web_hub.hr.leave.api.dto.LeaveTrackerResponse;
import com.web_hub.web_hub.hr.leave.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    /* ================= EMPLOYEE: APPLY LEAVE ================= */

    @PostMapping
    public ResponseEntity<LeaveResponse> applyLeave(@RequestBody LeaveRequest req) {
        return ResponseEntity.ok(leaveService.apply(req));
    }

    /* ================= EMPLOYEE: UPDATE LEAVE INFO ================= */

    @PutMapping("/{id}")
    public ResponseEntity<LeaveResponse> updateLeave(@PathVariable Long id, @RequestBody LeaveRequest req) {
        return ResponseEntity.ok(leaveService.updateLeave(id, req));
    }

    /* ================= EMPLOYEE: TRACKER BALANCES ================= */

    @GetMapping("/balances/my")
    public ResponseEntity<List<LeaveTrackerResponse>> myLeaveBalances() {
        return ResponseEntity.ok(leaveService.getMyLeaveBalances());
    }

    /* ================= EMPLOYEE: MY LEAVES ================= */

    @GetMapping("/my")
    public ResponseEntity<List<LeaveResponse>> myLeaves() {
        return ResponseEntity.ok(leaveService.getMyLeaves());
    }

    /* ================= HR: VIEW ALL LEAVES ================= */

    @GetMapping
    public ResponseEntity<List<LeaveResponse>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    /* ================= HR: APPROVE / REJECT ================= */

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveResponse> approveLeave(@PathVariable Long id) {
        return ResponseEntity.ok(leaveService.approveLeave(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveResponse> rejectLeave(@PathVariable Long id) {
        return ResponseEntity.ok(leaveService.rejectLeave(id));
    }

    /* ================= HR: SINGLE LEAVE ================= */

    @GetMapping("/{id}")
    public ResponseEntity<List<LeaveResponse>> getLeaveById(@PathVariable Long id) {
        // Keeping mapping signature clean with your getById service method
        return ResponseEntity.ok(List.of(leaveService.getById(id)));
    }
}