package com.web_hub.web_hub.auth.api.dto;


import jakarta.validation.constraints.NotBlank;

public record VerifyMfaRequest(

        String email,

        @NotBlank
        String otp

) {}
