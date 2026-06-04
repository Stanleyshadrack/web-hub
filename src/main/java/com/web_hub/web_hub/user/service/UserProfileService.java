package com.web_hub.web_hub.user.service;

import com.web_hub.web_hub.exception.AuthException;
import com.web_hub.web_hub.hr.Employees.model.Employee;
import com.web_hub.web_hub.user.model.User;
import com.web_hub.web_hub.user.repository.UserRepository;
import com.web_hub.web_hub.user.api.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    public UserProfileResponse getMyProfile() {
        User user = getCurrentUser();
        Employee employee = user.getEmployee();

        // Safely extract from Employee object or fallback if null
        String firstName = (employee != null) ? employee.getFirstName() : null;
        String lastName = (employee != null) ? employee.getLastName() : null;
        String jobTitle = (employee != null) ? employee.getJobTitle() : null;
        String phone = (employee != null) ? employee.getPhone() : null;
        String department = (employee != null) ? employee.getDepartment() : null;

        // Parse String date to LocalDate safely for the DTO response
        LocalDate joinDate = null;
        if (employee != null && employee.getStartDate() != null) {
            try {
                joinDate = LocalDate.parse(employee.getStartDate());
            } catch (Exception e) {
                // Fallback if formatting doesn't perfectly match standard ISO format
            }
        }

        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                firstName,
                lastName,
                jobTitle,
                phone,
                department,
                null, // Location field can be mapped if added to Employee entity later
                joinDate,
                user.getRole(),
                user.isActive()
        );
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new AuthException("User not found"));
    }
}