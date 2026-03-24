package com.web_hub.web_hub.admin.audit;

import com.web_hub.web_hub.admin.dto.AuditResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {

    public List<AuditResponse> getLogs() {
        return List.of(); // later DB
    }
}
