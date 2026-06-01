package com.pontosale.service;

import com.pontosale.dto.RoleResponseDTO;
import com.pontosale.dto.RoleSaveDTO;
import com.pontosale.entity.Role;
import com.pontosale.entity.Usuario;

import java.util.List;

public interface RoleService {

    public List<Role> findAll();

    public RoleResponseDTO getByIdAndPermissions(Long id);

    public Role findById(Long id);

    public Role create(RoleSaveDTO roleSaveDTO, Usuario usuario);

    public Role update(RoleSaveDTO roleSaveDTO, Usuario usuario);

    public void delete(Role role);

}
