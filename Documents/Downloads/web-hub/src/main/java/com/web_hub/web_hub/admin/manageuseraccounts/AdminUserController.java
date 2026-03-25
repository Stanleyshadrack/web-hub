package com.web_hub.web_hub.admin.manageuseraccounts;
import com.web_hub.web_hub.admin.dto.CreateUserRequest;
import com.web_hub.web_hub.admin.dto.UpdateUserRequest;
import com.web_hub.web_hub.admin.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    /* ================= CREATE ================= */

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.ok(adminUserService.create(request));
    }

    /* ================= GET ALL ================= */

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(adminUserService.getAll());
    }

    /* ================= UPDATE ================= */

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.ok(adminUserService.update(id, request));
    }

    /* ================= SUSPEND ================= */

    @PutMapping("/{id}/suspend")
    public ResponseEntity<String> suspend(@PathVariable Long id) {
        adminUserService.suspend(id);
        return ResponseEntity.ok("User suspended");
    }

    /* ================= RESET PASSWORD ================= */

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable Long id) {
        adminUserService.resetPassword(id);
        return ResponseEntity.ok("Password reset");
    }
}
