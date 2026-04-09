package com.pontosale.controller;

import com.pontosale.dto.UsuarioCreateDTO;
import com.pontosale.entity.Usuario;
import com.pontosale.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.prefix}/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/usuario")
    public ResponseEntity<?> cadastrarUsuario(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam MultipartFile image,
            Authentication authentication
    ) {
        try {

            String tokenEmail = authentication.getName();

            UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();

            usuarioCreateDTO.setNome(nome);
            usuarioCreateDTO.setEmail(email);
            usuarioCreateDTO.setSenha(senha);

            try {
                usuarioCreateDTO.setImage(image.getBytes());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            usuarioService.create(usuarioCreateDTO, tokenEmail);

            return ResponseEntity.ok("Usuário cadastrado com sucesso");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/ponto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registrarPonto(@RequestParam MultipartFile file,
                                            Authentication authentication) throws Exception {
        String email = authentication.getName();

        byte[] image = file.getBytes();

        if (usuarioService.logTimePoint(image, email)) {
            return  ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
