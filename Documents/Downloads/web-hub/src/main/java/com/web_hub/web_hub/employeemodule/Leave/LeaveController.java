package com.web_hub.web_hub.employeemodule.Leave;
import com.web_hub.web_hub.employeemodule.dto.CreateLeaveRequest;
import com.web_hub.web_hub.employeemodule.dto.LeaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    public ResponseEntity<LeaveResponse> applyLeave(
            @RequestBody CreateLeaveRequest req
    ) {
        return ResponseEntity.ok(leaveService.apply(req));
    }

    @GetMapping("/my")
    public ResponseEntity<List<LeaveResponse>> myLeaves() {
        return ResponseEntity.ok(leaveService.getMyLeaves());
    }
}
