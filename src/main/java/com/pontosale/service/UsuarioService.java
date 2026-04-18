package com.pontosale.service;

import com.pontosale.dto.UsuarioCreateDTO;
import com.pontosale.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

//    List<Usuario> findAll();

    void create(UsuarioCreateDTO usuarioCreateDTO, String email);

    boolean logTimePoint(byte[] image, String email);

    Optional<Usuario> findByEmail(String email);

}
