package com.pontosale.controller;

import com.pontosale.dto.PontoSaveDTO;
import com.pontosale.dto.PontoUpdateDTO;
import com.pontosale.entity.Ponto;
import com.pontosale.entity.Usuario;
import com.pontosale.service.PontoService;
import com.pontosale.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.prefix}/ponto")
public class PontoController {

    @Autowired
    PontoService pontoService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping(value = "/ponto")
    public ResponseEntity<List<Ponto>> getAllPonto(Authentication authentication) {
        final String email = authentication.getName();

        Usuario usuario = usuarioService.findByEmail(email).get();

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(pontoService.getPontos(usuario));
    }

    @PostMapping(value = "/ponto")
    public ResponseEntity<Ponto> savePonto(@RequestBody PontoSaveDTO pontoSaveDTO, Authentication authentication) {
        String email = authentication.getName();

        Optional<Usuario> usuario = usuarioService.findByEmail(email);

        if (!usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(pontoService.savePonto(pontoSaveDTO, usuario.get()));
    }

    @PutMapping(value = "/ponto")
    public ResponseEntity<Ponto> updatePonto(@RequestBody PontoUpdateDTO pontoUpdateDTO, Authentication authentication) {
        String email = authentication.getName();

        Usuario usuario = usuarioService.findByEmail(email).get();

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Ponto ponto = pontoService.updatePonto(pontoUpdateDTO, usuario).get();

        if (ponto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(ponto);
    }

    @DeleteMapping(value = "/ponto/{id}")
    public ResponseEntity<?> deletePonto(@PathVariable Long id) {
        pontoService.deletePonto(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
