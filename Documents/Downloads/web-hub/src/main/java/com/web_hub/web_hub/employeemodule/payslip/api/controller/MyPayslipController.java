package com.web_hub.web_hub.employeemodule.payslip.api.controller;

import com.web_hub.web_hub.employeemodule.payslip.service.MyPayslipService;
import com.web_hub.web_hub.hr.payroll.model.Payroll;
import com.web_hub.web_hub.user.model.User;
import com.web_hub.web_hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees/payslips")
@RequiredArgsConstructor
public class MyPayslipController {

    private final MyPayslipService myPayslipService;
    private final UserRepository userRepository;
    @GetMapping
    public List<Payroll> getMyPayslips(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return myPayslipService.getMyPayslips(user);
    }
    }
