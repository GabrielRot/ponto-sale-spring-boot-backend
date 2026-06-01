package com.pontosale.repository;

import com.pontosale.entity.Role;
import com.pontosale.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    void deleteByRole(Role role);

    List<RolePermission> findByRole(Role role);
}
