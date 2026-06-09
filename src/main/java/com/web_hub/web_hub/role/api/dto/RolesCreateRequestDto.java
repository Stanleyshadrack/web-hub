package com.web_hub.web_hub.role.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesCreateRequestDto {
    private String roleName;
    private List<Long> permissionIds;
}
