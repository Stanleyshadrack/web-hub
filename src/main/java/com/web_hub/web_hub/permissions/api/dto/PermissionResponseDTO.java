package com.web_hub.web_hub.permissions.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponseDTO {
    private Long id;
    private String permissionName;
}
