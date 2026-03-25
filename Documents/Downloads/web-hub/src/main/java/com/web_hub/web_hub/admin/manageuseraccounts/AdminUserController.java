package com.web_hub.web_hub.admin.manageuseraccounts;

import com.web_hub.web_hub.admin.dto.CreateUserRequest;
import com.web_hub.web_hub.admin.dto.UpdateUserRequest;
import com.web_hub.web_hub.admin.dto.UserResponse;
import com.web_hub.web_hub.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /* ================= CREATE USER (ADMIN) ================= */

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.ok(adminUserService.createUser(request));
    }

    /* ================= SELF REGISTER ================= */
    // ⚠️ Optional: This usually belongs in AuthController

    @PostMapping("/self-register")
    public ResponseEntity<UserResponse> selfRegister(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(adminUserService.selfRegister(request));
    }

    /* ================= GET ALL USERS ================= */

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    /* ================= UPDATE USER ================= */

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.ok(adminUserService.updateUser(id, request));
    }

    /* ================= SUSPEND USER ================= */

    @PatchMapping("/{id}/suspend")
    public ResponseEntity<Void> suspendUser(@PathVariable Long id) {
        adminUserService.suspendUser(id);
        return ResponseEntity.noContent().build();
    }

    /* ================= RESET PASSWORD ================= */

    @PatchMapping("/{id}/reset-password")
    public ResponseEntity<UserResponse> resetPassword(@PathVariable Long id) {
        return ResponseEntity.ok(adminUserService.resetPassword(id));
    }
}
