package com.web_hub.web_hub.admin.assetmanagement.api.dto;

import lombok.Data;

@Data
public class AssetRequest {
    private String name;
    private String type;
    private String serialNumber;
    private Long employeeId;
}
