package com.web_hub.web_hub.admin.department;

import com.web_hub.web_hub.admin.dto.DepartmentRequest;
import com.web_hub.web_hub.admin.dto.DepartmentResponse;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    public DepartmentResponse create(DepartmentRequest req) {
        return new DepartmentResponse(1L, req.name());
    }
}
