package com.web_hub.web_hub.employeemodule.myprojects.api.controller;

import com.web_hub.web_hub.employeemodule.myprojects.service.MyProjectService;
import com.web_hub.web_hub.projects.project.model.Project;
import com.web_hub.web_hub.user.model.User;
import com.web_hub.web_hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/my/projects")
@RequiredArgsConstructor
public class MyProjectController {

    private final MyProjectService service;
    private final UserRepository userRepository;

    @GetMapping
    public List<Project> getMyProjects(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return service.getMyProjects(user);
    }
}
