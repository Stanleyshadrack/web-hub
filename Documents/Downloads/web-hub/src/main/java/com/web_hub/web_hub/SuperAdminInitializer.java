package com.web_hub.web_hub;

import com.web_hub.web_hub.role.Role;
import com.web_hub.web_hub.user.User;
import com.web_hub.web_hub.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Configuration
@RequiredArgsConstructor
public class SuperAdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.super-admin.email}")
    private String email;

    @Value("${app.super-admin.password}")
    private String password;

    @Override
    public void run(String... args) {

        // ✅ Do NOT recreate if already exists
        if (userRepository.findByEmail(email).isPresent()) {
            return;
        }

        User superAdmin = User.builder()
                .username("superadmin")
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.SUPER_ADMIN)

                /* =========================
                   ACCOUNT STATUS
                ========================= */
                .approved(true)                 // 👑 System-approved
                .active(true)                   // 🔓 Can log in
                .emailVerified(true)

                /* =========================
                   SECURITY FLAGS
                ========================= */
                .passwordSet(true)              // ✅ Password exists
                .forcePasswordChange(true)      // 🔐 MUST change on first login
                .failedLoginAttempts(0)
                .lockedUntil(null)

                /* =========================
                   ONBOARDING (SYSTEM USER)
                ========================= */
                .onboardingCompleted(true)      // ❌ No onboarding required

                /* =========================
                   AUDIT / META
                ========================= */
                .createdAt(Instant.now())
                .updatedAt(Instant.now())

                .build();

        userRepository.save(superAdmin);

        System.out.println("✅ Default SUPER ADMIN created: " + email);
        System.out.println("🔐 Password change required on first login");
    }
}
