package com.pontosale.repository;

import com.pontosale.entity.Role;
import com.pontosale.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    void deleteByRole(Role role);
}
