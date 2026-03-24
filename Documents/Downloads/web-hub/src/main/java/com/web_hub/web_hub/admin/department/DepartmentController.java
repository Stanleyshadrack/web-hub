package com.web_hub.web_hub.admin.department;

import com.web_hub.web_hub.admin.dto.DepartmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/departments")
@RequiredArgsConstructor

public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DepartmentRequest req) {
        return ResponseEntity.ok(departmentService.create(req));
    }
}
