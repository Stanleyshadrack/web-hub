package com.web_hub.web_hub.admin.manageuseraccounts;
import com.web_hub.web_hub.admin.dto.CreateUserRequest;
import com.web_hub.web_hub.admin.dto.UpdateUserRequest;
import com.web_hub.web_hub.admin.dto.UserResponse;
import com.web_hub.web_hub.exception.AuthException;
import com.web_hub.web_hub.role.Role;
import com.web_hub.web_hub.user.User;
import com.web_hub.web_hub.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* ================= CREATE USER ================= */

    public UserResponse create(CreateUserRequest req) {

        String email = req.email().trim().toLowerCase();

        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new AuthException("User already exists");
        }

        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        User user = User.builder()
                .email(email)
                .username(req.username())
                .password(passwordEncoder.encode(tempPassword))
                .role(req.role() != null ? req.role() : Role.USER)
                .active(true)
                .build();

        userRepository.save(user);

        return map(user);
    }

    /* ================= GET ALL USERS ================= */

    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    /* ================= UPDATE USER ================= */

    public UserResponse update(Long id, UpdateUserRequest req) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AuthException("User not found"));

        if (req.username() != null) {
            user.setUsername(req.username());
        }

        if (req.role() != null) {
            user.setRole(req.role());
        }

        if (req.active() != null) {
            user.setActive(req.active());
        }

        userRepository.save(user);

        return map(user);
    }

    /* ================= SUSPEND ================= */

    public void suspend(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AuthException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }

    /* ================= RESET PASSWORD ================= */

    public void resetPassword(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AuthException("User not found"));

        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
    }

    /* ================= MAPPER ================= */

    private UserResponse map(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.isActive()
        );
    }
}
