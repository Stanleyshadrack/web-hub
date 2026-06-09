package com.web_hub.web_hub.permissions.api.controller;

import com.web_hub.web_hub.permissions.api.dto.PermissionRequestDTO;
import com.web_hub.web_hub.permissions.api.dto.PermissionResponseDTO; // ◄ Make sure this is imported
import com.web_hub.web_hub.permissions.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    // CREATE
    @PostMapping
    public ResponseEntity<PermissionResponseDTO> create(@RequestBody PermissionRequestDTO dto) {
        return ResponseEntity.ok(permissionService.createPermission(dto));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<PermissionResponseDTO>> getAll() {
        return ResponseEntity.ok(permissionService.getAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PermissionRequestDTO dto) {
        return ResponseEntity.ok(permissionService.updatePermission(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        permissionService.deletePermission(id);

        Map<String, String> response = Map.of(
                "message", "Permission deleted successfully",
                "status", "SUCCESS"
        );

        return ResponseEntity.ok(response);
    }
}