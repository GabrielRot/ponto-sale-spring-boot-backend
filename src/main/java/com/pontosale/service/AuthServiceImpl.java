package com.pontosale.service;

import com.pontosale.dto.AuthDTO;
import com.pontosale.dto.AuthResponseDTO;
import com.pontosale.security.JwtUtil;
import com.pontosale.entity.Usuario;
import com.pontosale.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public AuthResponseDTO login(AuthDTO authDTO) {

        Usuario usuario = usuarioRepository.findByEmail(authDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

       validatePassword(authDTO, usuario.getSenha());

        if (usuario.getStatus() != StatusUsuario.ATIVO) {
            throw new RuntimeException("Usuário inativo");
        }

        System.out.println(usuario.getEmail());

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();

        authResponseDTO.setToken(jwtUtil.generateToken(usuario.getEmail()));

        return authResponseDTO;
    }

    private void validatePassword(AuthDTO authDTO, String senha) {
        if (!authDTO.getSenha().equals(senha)) {
            throw new RuntimeException("Invalid password");
        }
    }

}
