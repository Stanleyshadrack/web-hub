package com.web_hub.web_hub.employeemodule.Leave;
import com.web_hub.web_hub.employeemodule.dto.CreateLeaveRequest;
import com.web_hub.web_hub.employeemodule.dto.LeaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {

    public LeaveResponse apply(CreateLeaveRequest req) {
        return new LeaveResponse(
                1L,
                req.type(),
                req.startDate(),
                req.endDate(),
                "PENDING"
        );
    }

    public List<LeaveResponse> getMyLeaves() {
        return List.of(); // later from DB
    }
}
