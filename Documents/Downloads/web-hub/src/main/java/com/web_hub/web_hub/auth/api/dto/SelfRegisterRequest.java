package com.web_hub.web_hub.auth.api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SelfRegisterRequest(

        @Email
        String email,

        @NotBlank
        String password

) {}
