package com.web_hub.web_hub.permissions.service;

import com.web_hub.web_hub.exception.ResourceNotFoundException;
import com.web_hub.web_hub.permissions.api.dto.PermissionRequestDTO;
import com.web_hub.web_hub.permissions.api.dto.PermissionResponseDTO; // ◄ Ensure this is imported
import com.web_hub.web_hub.permissions.model.PermissionsModel;
import com.web_hub.web_hub.permissions.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    // CREATE -> Returns clean JSON DTO
    @Transactional
    public PermissionResponseDTO createPermission(PermissionRequestDTO dto) {
        if (permissionRepository.existsByPermissionName(dto.getPermissionName())) {
            throw new DataIntegrityViolationException("Permission with name '" + dto.getPermissionName() + "' already exists");
        }

        PermissionsModel p = new PermissionsModel();
        p.setPermissionName(dto.getPermissionName());
        return mapToResponse(permissionRepository.save(p));
    }

    // UPDATE -> Returns clean JSON DTO
    @Transactional
    public PermissionResponseDTO updatePermission(Long id, PermissionRequestDTO dto) {
        PermissionsModel p = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));

        if (dto.getPermissionName() != null && !dto.getPermissionName().trim().isEmpty()) {
            if (permissionRepository.existsByPermissionNameAndIdNot(dto.getPermissionName(), id)) {
                throw new DataIntegrityViolationException("Permission with name '" + dto.getPermissionName() + "' already exists on another record");
            }
            p.setPermissionName(dto.getPermissionName());
        }

        return mapToResponse(permissionRepository.save(p));
    }

    // GET ALL -> Returns a List of JSON DTOs
    public List<PermissionResponseDTO> getAll() {
        return permissionRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID -> Returns clean JSON DTO
    public PermissionResponseDTO getById(Long id) {
        PermissionsModel p = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        return mapToResponse(p);
    }

    // DELETE
    @Transactional
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }

    // HELPER MAPPING METHOD (Entity -> DTO)
    private PermissionResponseDTO mapToResponse(PermissionsModel permission) {
        PermissionResponseDTO dto = new PermissionResponseDTO();
        dto.setId(permission.getId());
        dto.setPermissionName(permission.getPermissionName());
        return dto;
    }
}