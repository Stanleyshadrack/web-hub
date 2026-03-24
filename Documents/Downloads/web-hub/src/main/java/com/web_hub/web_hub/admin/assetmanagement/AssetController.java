package com.web_hub.web_hub.admin.assetmanagement;
import com.web_hub.web_hub.admin.dto.AssetRequest;
import com.web_hub.web_hub.admin.dto.AssetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/admin/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    /* ================= CREATE ================= */

    @PostMapping
    public ResponseEntity<AssetResponse> create(
            @RequestBody AssetRequest request
    ) {
        return ResponseEntity.ok(assetService.create(request));
    }

    /* ================= GET ALL ================= */

    @GetMapping
    public ResponseEntity<List<AssetResponse>> getAll() {
        return ResponseEntity.ok(assetService.getAll());
    }
}
