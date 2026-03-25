package com.web_hub.web_hub.admin.manageuseraccounts;

import com.web_hub.web_hub.admin.dto.CreateUserRequest;
import com.web_hub.web_hub.admin.dto.UpdateUserRequest;
import com.web_hub.web_hub.admin.dto.UserResponse;
import com.web_hub.web_hub.dto.RegisterRequest;
import com.web_hub.web_hub.exception.AuthException;
import com.web_hub.web_hub.role.Role;
import com.web_hub.web_hub.user.User;
import com.web_hub.web_hub.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* ================= CREATE USER (ADMIN) ================= */

    public UserResponse createUser(CreateUserRequest req) {
        String email = normalizeEmail(req.email());
        validateEmailNotTaken(email);

        String tempPassword = generateTempPassword();

        User user = User.builder()
                .email(email)
                .username(req.username())
                .password(passwordEncoder.encode(tempPassword))
                .role(req.role() != null ? req.role() : Role.USER)
                .active(true)
                .build();

        userRepository.save(user);
        return mapToResponse(user, tempPassword);
    }

    /* ================= SELF REGISTER ================= */

    public UserResponse selfRegister(RegisterRequest req) {
        String email = normalizeEmail(req.email());
        validateEmailNotTaken(email);

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(req.password()))
                .role(Role.USER)
                .active(true)
                .build();

        userRepository.save(user);
        return mapToResponse(user, null);
    }

    /* ================= GET ALL ================= */

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapToResponse(user, null))
                .toList();
    }

    /* ================= UPDATE ================= */

    public UserResponse updateUser(Long id, UpdateUserRequest req) {
        User user = getUserById(id);

        if (req.username() != null) user.setUsername(req.username());
        if (req.role() != null) user.setRole(req.role());
        if (req.active() != null) user.setActive(req.active());

        userRepository.save(user);
        return mapToResponse(user, null);
    }

    /* ================= SUSPEND ================= */

    public void suspendUser(Long id) {
        User user = getUserById(id);
        user.setActive(false);
        userRepository.save(user);
    }

    /* ================= RESET PASSWORD ================= */

    public UserResponse resetPassword(Long id) {
        User user = getUserById(id);

        String tempPassword = generateTempPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));

        userRepository.save(user);
        return mapToResponse(user, tempPassword);
    }

    /* ================= HELPER METHODS ================= */

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private void validateEmailNotTaken(String email) {
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new AuthException("User already exists");
        }
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AuthException("User not found"));
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private UserResponse mapToResponse(User user, String tempPassword) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.isActive(),
                tempPassword
        );
    }
}
