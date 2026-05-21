package com.web_hub.web_hub.admin.assetmanagement.repository;

import com.web_hub.web_hub.admin.assetmanagement.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
