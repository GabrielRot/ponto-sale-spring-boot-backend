package com.pontosale.controller;

import com.pontosale.dto.RoleResponseDTO;
import com.pontosale.dto.RoleSaveDTO;
import com.pontosale.entity.Role;
import com.pontosale.entity.Usuario;
import com.pontosale.service.RoleService;
import com.pontosale.service.UsuarioService;
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
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Role>> findAll(Authentication authentication) {


        List<Role> roles = roleService.findAll();

        return ResponseEntity.ok().body(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getByIdAndPermissions(@PathVariable Long id, Authentication authentication) {
        RoleResponseDTO roleResponseDTO = roleService.getByIdAndPermissions(id);

        return ResponseEntity.ok().body(roleResponseDTO);
    }

    @PostMapping
    public ResponseEntity<Role> save(@RequestBody RoleSaveDTO roleSaveDTO, Authentication authentication) {
        System.out.println("post");

        Usuario usuario = usuarioService.findByEmail(authentication.getName()).get();

        Role role = roleService.create(roleSaveDTO, usuario);


        return ResponseEntity.ok().body(role);
    }

    @PutMapping
    public ResponseEntity<Role> update(@RequestBody RoleSaveDTO roleSaveDTO, Authentication authentication) {
        Role role = roleService.findById(roleSaveDTO.getId());

        if (role == null) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioService.findByEmail(authentication.getName()).get();

        roleService.update(roleSaveDTO, usuario);

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
