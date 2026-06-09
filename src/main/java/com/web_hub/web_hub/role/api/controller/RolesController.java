package com.web_hub.web_hub.role.api.controller;

import com.web_hub.web_hub.role.api.dto.RolesCreateRequestDto;
import com.web_hub.web_hub.role.api.dto.RolesCreateResponseDto;
import com.web_hub.web_hub.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolesController {
    private final RoleService roleService;

    // CREATE
    @PostMapping
    public ResponseEntity<RolesCreateResponseDto> create(@RequestBody RolesCreateRequestDto dto) {
        return ResponseEntity.ok(roleService.createRole(dto));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<RolesCreateResponseDto>> getAll() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<RolesCreateResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<RolesCreateResponseDto> update(
            @PathVariable Long id,
            @RequestBody RolesCreateRequestDto dto) {
        return ResponseEntity.ok(roleService.updateRole(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        roleService.deleteRole(id);

        // Changing status to 200 OK and returning a structured map
        Map<String, String> response = Map.of(
                "message", "Role deleted successfully",
                "status", "SUCCESS"
        );

        return ResponseEntity.ok(response);
    }
}