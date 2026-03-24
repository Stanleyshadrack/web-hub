package com.web_hub.web_hub.employeemodule.Employee;

import com.web_hub.web_hub.employeemodule.dto.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/me")
    public ResponseEntity<EmployeeResponse> getMyProfile() {
        return ResponseEntity.ok(employeeService.getMyProfile());
    }
}
