package com.web_hub.web_hub.assetManagement.api.dto;

import com.web_hub.web_hub.assetManagement.model.AssetStatus;
import lombok.Data;

@Data
public class UnassignAssetRequest {
    private AssetStatus status;
    private String reason;
}
