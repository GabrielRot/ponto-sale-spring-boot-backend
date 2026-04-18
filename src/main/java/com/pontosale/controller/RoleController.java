package com.pontosale.controller;

import com.pontosale.dto.RoleSaveDTO;
import com.pontosale.entity.Role;
import com.pontosale.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> findAll(Authentication authentication) {


        List<Role> roles = roleService.findAll();

        return ResponseEntity.ok().body(roles);
    }

    @PostMapping
    public ResponseEntity<Role> save(@RequestBody RoleSaveDTO roleSaveDTO) {
        Role role = roleService.create(roleSaveDTO);


        return ResponseEntity.ok().body(role);
    }

    @PutMapping
    public ResponseEntity<Role> update(@RequestParam Long id, @RequestBody RoleSaveDTO roleSaveDTO) {
        Role role = roleService.findById(id);

        if (role == null) {
            return ResponseEntity.notFound().build();
        }

        role.setNome(roleSaveDTO.getNome());
        role.setDescricao(roleSaveDTO.getDescricao());



        return ResponseEntity.ok().body(role);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        Role role = roleService.findById(id);

        if (role == null) {
            return  ResponseEntity.notFound().build();
        }

        roleService.delete(role);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
