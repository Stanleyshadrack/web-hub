package com.web_hub.web_hub.assetManagement.service;


import com.web_hub.web_hub.assetManagement.api.dto.*;
import com.web_hub.web_hub.assetManagement.model.Asset;
import com.web_hub.web_hub.assetManagement.model.AssetStatus;
import com.web_hub.web_hub.assetManagement.repository.AssetManagementRepository;
import com.web_hub.web_hub.exception.ResourceNotFoundException;
import com.web_hub.web_hub.user.model.User;
import com.web_hub.web_hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetManagementService {

    private final AssetManagementRepository assetRepository;
    private final UserRepository userRepository;

    // 1. GET DASHBOARD METRICS
    @Transactional(readOnly = true)
    public AssetDashboardSummaryDto getDashboardSummary() {
        long total = assetRepository.count();
        long assigned = assetRepository.countByStatus(AssetStatus.ASSIGNED);
        long available = assetRepository.countByStatus(AssetStatus.AVAILABLE);
        long inRepair = assetRepository.countByStatus(AssetStatus.IN_REPAIR);

        return new AssetDashboardSummaryDto(total, assigned, available, inRepair);
    }

    // 2. GET ALL / FILTERED ASSETS (Routes explicitly based on presence of params)
    @Transactional(readOnly = true)
    public List<AssetResponseDto> getAllAssets(String search, AssetStatus status) {
        String cleanSearch = (search != null && !search.trim().isEmpty()) ? search.trim() : null;
        boolean hasSearch = cleanSearch != null;

        List<Asset> assets;

        if (status != null && hasSearch) {
            assets = assetRepository.findByStatusAndSearch(status, cleanSearch);
        } else if (status != null) {
            assets = assetRepository.findByStatus(status);
        } else if (hasSearch) {
            assets = assetRepository.findBySearch(cleanSearch);
        } else {
            assets = assetRepository.findAll();
        }

        return assets.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    // 3. CREATE NEW ASSET
    @Transactional
    public AssetResponseDto createAsset(AssetCreateRequestDto dto) {
        if (assetRepository.existsBySerialNumber(dto.getSerialNumber())) {
            throw new DataIntegrityViolationException("Asset with serial number '" + dto.getSerialNumber() + "' already exists");
        }

        Asset asset = new Asset();
        asset.setName(dto.getName());
        asset.setCategory(dto.getCategory());
        asset.setSerialNumber(dto.getSerialNumber());
        asset.setAssetCondition(dto.getAssetCondition());
        asset.setStatus(AssetStatus.AVAILABLE);
        asset.setAssetCode(generateNextAssetCode());

        return mapToResponseDto(assetRepository.save(asset));
    }

    // 4. ASSIGN ASSET TO EMPLOYEE
    @Transactional
    public AssetResponseDto assignAsset(Long id, AssetAssignmentRequestDto dto) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));

        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new IllegalStateException("Asset with code '" + asset.getAssetCode() +
                    "' cannot be assigned because its current status is " + asset.getStatus() +
                    ". It must be AVAILABLE to be assigned.");
        }
        if (!userRepository.existsById(dto.getUserId())) {
            throw new ResourceNotFoundException("User not found with id: " + dto.getUserId());
        }

        // Fetch the admin/manager making the assignment
        User assigner = userRepository.findById(Long.valueOf(dto.getAssignedBy()))
                .orElseThrow(() -> new ResourceNotFoundException("Assigning user not found with id: " + dto.getAssignedBy()));

        // Set assignment details
        asset.setAssignedUserId(String.valueOf(dto.getUserId()));
        asset.setAssignmentDate(LocalDate.now());
        asset.setStatus(AssetStatus.ASSIGNED);

        // FIX: Extract the name from the fetched 'assigner' entity instead of the DTO
        asset.setAssignedBy(assigner.getUsername()); // <-- Use your actual User entity getter here

        // Housekeeping: Clear previous unassignment data if this asset is being reassigned
        asset.setUnassignmentDate(null);
        asset.setUnassignReason(null);

        return mapToResponseDto(assetRepository.save(asset));
    }


    // 5. UNASSIGN ASSET (Return to Available Pool)
    @Transactional
    public AssetResponseDto unassignAsset(Long id, UnassignAssetRequest request) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));

        // 👇 NEW VALIDATION: Ensure the asset is actually assigned before allowing unassignment
        if (asset.getStatus() != AssetStatus.ASSIGNED || asset.getAssignedUserId() == null) {
            throw new IllegalStateException("Asset with code '" + asset.getAssetCode() +
                    "' cannot be unassigned because it is not currently assigned to anyone.");
        }

        // Validate the requested target status (AVAILABLE or IN_REPAIR)
        if (request.getStatus() != AssetStatus.AVAILABLE && request.getStatus() != AssetStatus.IN_REPAIR) {
            throw new IllegalArgumentException("Status during unassignment must be either AVAILABLE or IN_REPAIR");
        }

        // Clear assignment details
        asset.setAssignedUserId(null);
        asset.setAssignmentDate(null);
        asset.setAssignedBy(null);

        // Record the exact date the asset was returned/unassigned
        asset.setUnassignmentDate(LocalDate.now());

        // Apply new status and optional reason
        asset.setStatus(request.getStatus());

        if (request.getReason() != null && !request.getReason().trim().isEmpty()) {
            asset.setUnassignReason(request.getReason());
        }

        return mapToResponseDto(assetRepository.save(asset));
    }

    /*------------------------------------------------------------------------
    // 4. UPDATE ASSET
    ---------------------------------------------------------------------------*/

    @Transactional
    public AssetResponseDto updateAsset(Long id, AssetUpdateRequestDto dto) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));

        if (dto.getName() != null) {
            asset.setName(dto.getName());
        }

        if (dto.getCategory() != null) {
            asset.setCategory(dto.getCategory());
        }

        if (dto.getSerialNumber() != null) {

            if (!asset.getSerialNumber().equals(dto.getSerialNumber())
                    && assetRepository.existsBySerialNumber(dto.getSerialNumber())) {
                throw new DataIntegrityViolationException(
                        "Asset with serial number '" + dto.getSerialNumber() + "' already exists"
                );
            }
            asset.setSerialNumber(dto.getSerialNumber());
        }

        if (dto.getAssetCondition() != null) {
            asset.setAssetCondition(dto.getAssetCondition());
        }

        return mapToResponseDto(assetRepository.save(asset));
    }
    // --- Private Helper Methods ---

    private String generateNextAssetCode() {
        return assetRepository.findFirstByOrderByIdDesc()
                .map(lastAsset -> {
                    long nextNumber = lastAsset.getId() + 1;
                    return String.format("AS%03d", nextNumber);
                })
                .orElse("AS001");
    }

    private AssetResponseDto mapToResponseDto(Asset asset) {
        return new AssetResponseDto(
                asset.getId(),
                asset.getAssetCode(),
                asset.getName(),
                asset.getCategory(),
                asset.getSerialNumber(),
                asset.getAssignedUserId(),
                asset.getAssignedToDepartment(),

                asset.getAssignmentDate(),
                asset.getAssetCondition(),
                asset.getStatus(),
                asset.getAssignedBy(),
                asset.getUnassignmentDate()
        );
    }
}