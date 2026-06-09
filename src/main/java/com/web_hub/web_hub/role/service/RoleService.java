package com.web_hub.web_hub.role.service;

import com.web_hub.web_hub.exception.ResourceNotFoundException;
import com.web_hub.web_hub.permissions.api.dto.PermissionResponseDTO;
import com.web_hub.web_hub.permissions.model.PermissionsModel;
import com.web_hub.web_hub.permissions.repository.PermissionRepository;
import com.web_hub.web_hub.role.api.dto.RolesCreateRequestDto;
import com.web_hub.web_hub.role.api.dto.RolesCreateResponseDto;
import com.web_hub.web_hub.role.model.RolesModel;
import com.web_hub.web_hub.role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    // CREATE
    @Transactional
    public RolesCreateResponseDto createRole(RolesCreateRequestDto dto) {
        RolesModel role = new RolesModel();
        role.setRoleName(dto.getRoleName());

        List<PermissionsModel> perms = permissionRepository.findAllByIdIn(dto.getPermissionIds());
        role.setPermissions(new HashSet<>(perms));

        return mapToResponse(roleRepository.save(role));
    }

    // GET ALL
    public List<RolesCreateResponseDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    public RolesCreateResponseDto getRoleById(Long id) {
        RolesModel role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return mapToResponse(role);
    }

    // UPDATE
    @Transactional
    public RolesCreateResponseDto updateRole(Long id, RolesCreateRequestDto dto) {
        RolesModel role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        if (dto.getRoleName() != null && !dto.getRoleName().trim().isEmpty()) {
            role.setRoleName(dto.getRoleName());
        }

        if (dto.getPermissionIds() != null) {
            if (dto.getPermissionIds().isEmpty()) {
                role.getPermissions().clear();
            } else {
                List<PermissionsModel> perms = permissionRepository.findAllByIdIn(dto.getPermissionIds());
                role.setPermissions(new HashSet<>(perms));
            }
        }

        return mapToResponse(roleRepository.save(role));
    }

    // DELETE
    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }

    // HELPER MAPPING METHOD
    private RolesCreateResponseDto mapToResponse(RolesModel role) {
        List<PermissionResponseDTO> permissionDtos = role.getPermissions().stream()
                .map(permission -> {
                    PermissionResponseDTO dto = new PermissionResponseDTO();
                    dto.setId(permission.getId());
                    dto.setPermissionName(permission.getPermissionName());
                    return dto;
                })
                .toList();

        return new RolesCreateResponseDto(
                role.getRoleId(),
                role.getRoleName(),
                permissionDtos
        );
    }
}