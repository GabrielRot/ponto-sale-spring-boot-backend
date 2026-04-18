package com.pontosale.controller;

import com.pontosale.dto.PontoEditResponseDTO;
import com.pontosale.dto.PontoSaveDTO;
import com.pontosale.dto.PontoUpdateDTO;
import com.pontosale.dto.PontosRegistradosResponseDTO;
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

import java.util.ArrayList;
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

    @GetMapping(value = "/pontos-user")
    public ResponseEntity<List<PontosRegistradosResponseDTO>> getAllPontoByUser(Authentication authentication) {
        final String email = authentication.getName();

        Usuario usuario = usuarioService.findByEmail(email).get();

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        List<Ponto> pontos = pontoService.getAllPontoByUser(usuario);
        List<PontosRegistradosResponseDTO> pontosRegistradosResponseDTOS = new ArrayList<>();

        pontos.forEach(ponto -> {
            PontosRegistradosResponseDTO pontosRegistradosResponseDTO = new PontosRegistradosResponseDTO();

            pontosRegistradosResponseDTO.setId(ponto.getId());
            pontosRegistradosResponseDTO.setDataHoraAbertura(ponto.getDataHoraAbertura());
            pontosRegistradosResponseDTO.setDataHoraFechamento(ponto.getDataHoraFechamento());
            pontosRegistradosResponseDTO.setTipoInsercaoPonto(ponto.getTipoInsercaoPonto());

            pontosRegistradosResponseDTOS.add(pontosRegistradosResponseDTO);
        });

        return ResponseEntity.status(HttpStatus.OK).body(pontosRegistradosResponseDTOS);
    }

    @GetMapping(value = "/ponto/{id}")
    public ResponseEntity<PontoEditResponseDTO> getPontoById(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();

        Usuario usuario = usuarioService.findByEmail(email).get();

        Ponto ponto = pontoService.getPontoByIdAndUsuario(id, usuario);

        if (ponto == null) {
            return ResponseEntity.notFound().build();
        }

        PontoEditResponseDTO pontoEditResponseDTO = new PontoEditResponseDTO();

        pontoEditResponseDTO.setId(ponto.getId());
        pontoEditResponseDTO.setDataHoraAbertura(ponto.getDataHoraAbertura());
        pontoEditResponseDTO.setDataHoraFechamento(ponto.getDataHoraFechamento());
        pontoEditResponseDTO.setFotoUsuario(usuario.getFoto());
        pontoEditResponseDTO.setNomeUsuario(usuario.getNome());

        return ResponseEntity.status(HttpStatus.OK).body(pontoEditResponseDTO);
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
