package com.web_hub.web_hub.user.api;


import com.web_hub.web_hub.hr.Employees.EmployeeResponse;
import com.web_hub.web_hub.hr.Employees.EmployeeService;
import com.web_hub.web_hub.user.model.User;
import com.web_hub.web_hub.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor

public class UserProfileController {

    private final EmployeeService employeeService;
    private final UserProfileService userProfileService; // ✅ inject

    @GetMapping("/me")
    public EmployeeResponse getMyProfile() {

        User user = userProfileService.getCurrentUser(); // ✅ correct

        return employeeService.getMyProfile(user);
    }
}
