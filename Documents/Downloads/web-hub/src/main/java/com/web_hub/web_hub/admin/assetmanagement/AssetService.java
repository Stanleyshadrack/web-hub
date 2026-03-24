package com.web_hub.web_hub.admin.assetmanagement;
import com.web_hub.web_hub.admin.dto.AssetRequest;
import com.web_hub.web_hub.admin.dto.AssetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    public AssetResponse create(AssetRequest req) {
        return new AssetResponse(
                1L,
                req.name(),
                req.type(),
                req.assignedUserId()
        );
    }

    public List<AssetResponse> getAll() {
        return List.of();
    }
}
