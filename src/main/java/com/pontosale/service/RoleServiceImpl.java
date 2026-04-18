package com.pontosale.service;

import com.pontosale.dto.RoleSaveDTO;
import com.pontosale.entity.Role;
import com.pontosale.entity.RolePermission;
import com.pontosale.repository.RolePermissionRepository;
import com.pontosale.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public List<Role> findAll() {
        List<Role> roles = roleRepository.findAll();

        return roles;
    }

    @Override
    public Role findById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);

        return role;
    }

    @Override
    public Role create(RoleSaveDTO roleSaveDTO) {
        Role role = new Role();

        role.setNome(roleSaveDTO.getNome());
        role.setDescricao(roleSaveDTO.getDescricao());

        Role savedRole = roleRepository.save(role);

        List<RolePermission> rolePermissions = roleSaveDTO.getPermissoesRole()
                .stream()
                .map(permissaoRole -> {
                    RolePermission rolePermission = new RolePermission();

                    rolePermission.setRole(savedRole);
                    rolePermission.setPermissaoRole(permissaoRole);

                    return rolePermission;
                })
                .toList();

        rolePermissionRepository.saveAll(rolePermissions);

        return role;
    }

    @Override
    public Role update(Role role, List<PermissoesRole> permissoesRoles) {
        Role savedRole =  roleRepository.save(role);

        rolePermissionRepository.deleteByRole(savedRole);

        List<RolePermission> rolePermissions = permissoesRoles
                .stream()
                .map(permissaoRole -> {
                    RolePermission rolePermission = new RolePermission();

                    rolePermission.setRole(savedRole);
                    rolePermission.setPermissaoRole(permissaoRole);

                    return rolePermission;
                })
                .toList();

        rolePermissionRepository.saveAll(rolePermissions);

        return savedRole;
    }

    @Override
    public void delete(Role role) {

        rolePermissionRepository.deleteByRole(role);

        roleRepository.delete(role);

    }
}
