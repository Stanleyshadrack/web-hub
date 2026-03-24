package com.web_hub.web_hub.admin.rolesandpermissions;

import com.web_hub.web_hub.role.Role;
import com.web_hub.web_hub.admin.dto.AssignRoleRequest;
import com.web_hub.web_hub.user.User;
import com.web_hub.web_hub.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final UserRepository userRepository;

    public void assignRole(AssignRoleRequest req) {

        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.valueOf(req.role()));
        userRepository.save(user);
    }
}
