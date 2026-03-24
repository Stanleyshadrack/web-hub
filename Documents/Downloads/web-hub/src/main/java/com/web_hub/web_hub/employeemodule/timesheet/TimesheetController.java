package com.web_hub.web_hub.employeemodule.timesheet;
import com.web_hub.web_hub.employeemodule.dto.TimesheetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timesheets")
@RequiredArgsConstructor
public class TimesheetController {

    private final TimesheetService timesheetService;

    @PostMapping
    public ResponseEntity<?> logHours(@RequestBody TimesheetRequest req) {
        timesheetService.logHours(req);
        return ResponseEntity.ok("Logged");
    }
}
