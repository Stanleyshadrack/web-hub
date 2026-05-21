package com.web_hub.web_hub.admin.assetmanagement.api.controller;

import com.web_hub.web_hub.admin.assetmanagement.api.dto.AssetRequest;
import com.web_hub.web_hub.admin.assetmanagement.api.dto.AssetResponse;
import com.web_hub.web_hub.admin.assetmanagement.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public AssetResponse createAsset(@RequestBody AssetRequest request) {
        return assetService.createAsset(request);
    }

    @GetMapping
    public List<AssetResponse> getAllAssets() {
        return assetService.getAllAssets();
    }

    @PutMapping("/return/{id}")
    public void returnAsset(@PathVariable Long id) {
        assetService.returnAsset(id);
    }
}
