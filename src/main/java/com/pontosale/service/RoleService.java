package com.pontosale.service;

import com.pontosale.dto.RoleSaveDTO;
import com.pontosale.entity.Role;

import java.util.List;

public interface RoleService {

    public List<Role> findAll();

    public Role findById(Long id);

    public Role create(RoleSaveDTO roleSaveDTO);

    public Role update(Role role, List<PermissoesRole> permissoesRoles);

    public void delete(Role role);

}
