package com.web_hub.web_hub.admin.rolesandpermissions;

import com.web_hub.web_hub.admin.dto.AssignRoleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/assign")
    public ResponseEntity<?> assign(@RequestBody AssignRoleRequest req) {
        roleService.assignRole(req);
        return ResponseEntity.ok("Role assigned");
    }
}
