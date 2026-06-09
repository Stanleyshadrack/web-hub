package com.web_hub.web_hub.permissions.repository;

import com.web_hub.web_hub.permissions.model.PermissionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<PermissionsModel, Long> {
    List<PermissionsModel> findAllByIdIn(List<Long> ids);
    boolean existsByPermissionName(String permissionName);
    boolean existsByPermissionNameAndIdNot(String permissionName, Long id);
}
