package com.web_hub.web_hub.assetManagement.api.dto;


import lombok.Data;

@Data
public class AssetAssignmentRequestDto {
    private Long userId;
    private String assignedBy;
}
