package com.pontosale.service;

import com.pontosale.dto.RoleResponseDTO;
import com.pontosale.dto.RoleSaveDTO;
import com.pontosale.entity.Role;
import com.pontosale.entity.RolePermission;
import com.pontosale.entity.Usuario;
import com.pontosale.repository.RolePermissionRepository;
import com.pontosale.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public List<Role> findAll() {
        List<Role> roles = roleRepository.findAllByOrderByCriadoEmDesc();

        return roles;
    }

    @Override
    public RoleResponseDTO getByIdAndPermissions(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));

        List<RolePermission> rolePermissions = rolePermissionRepository.findByRole(role);

        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();

        roleResponseDTO.setNome(role.getNome());
        roleResponseDTO.setDescricao(role.getDescricao());

        roleResponseDTO.setPermissoes(rolePermissions.stream().map(RolePermission::getPermissaoRole).collect(Collectors.toList()));

        return roleResponseDTO;
    }

    @Override
    public Role findById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);

        return role;
    }

    @Override
    public Role create(RoleSaveDTO roleSaveDTO, Usuario usuario) {
        Role role = new Role();

        role.setNome(roleSaveDTO.getNome());
        role.setDescricao(roleSaveDTO.getDescricao());
        role.setCriadoEm(LocalDateTime.now());
        role.setCriadoPor(usuario);

        roleRepository.save(role);

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
    public Role update(RoleSaveDTO roleSaveDTO, Usuario usuario) {
        Role roleSave = roleRepository.findById(roleSaveDTO.getId())
                .orElseThrow();

        roleSave.setAlteradoEm(LocalDateTime.now());
        roleSave.setAlteradoPor(usuario);

        Role savedRole =  roleRepository.save(roleSave);

        List<PermissoesRole> permissoesRoles = roleSaveDTO.getPermissoesRole();

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
