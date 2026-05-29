package com.web_hub.web_hub.errorHandler;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL) // Hides the validationErrors field if it's empty
public record ApiErrorResponse(
        int status,
        String error,
        String message,
        Map<String, String> validationErrors,
        LocalDateTime timestamp
) {}
