package com.web_hub.web_hub.admin;

import com.web_hub.web_hub.role.Role;
import java.time.LocalDate;

public record UserResponse(
        Long id,
        String email,
        String username,

        // --- ADD THESE PROFILE FIELDS ---
        String firstName,
        String lastName,
        String jobTitle,
        String phoneNumber,
        String department,
        String location,
        LocalDate joinDate,
        // --------------------------------

        Role role,
        boolean active,
        String inviteToken
) {}
