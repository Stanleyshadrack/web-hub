package com.web_hub.web_hub.auth.api.dto;

public record RegisterRequest(
        String email,
        String password
) {}
