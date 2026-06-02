package com.web_hub.web_hub.user.api;

import com.web_hub.web_hub.hr.Employees.repository.EmployeeResponse;
import com.web_hub.web_hub.hr.Employees.service.EmployeeService;
import com.web_hub.web_hub.user.model.User;
import com.web_hub.web_hub.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor

public class UserProfileController {

    private final EmployeeService employeeService;
    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public EmployeeResponse getMyProfile() {

        User user = userProfileService.getCurrentUser();

        return employeeService.getMyProfile(user);
    }
}
