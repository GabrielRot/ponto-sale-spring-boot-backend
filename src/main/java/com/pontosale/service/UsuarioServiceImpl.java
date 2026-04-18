package com.pontosale.service;

import com.pontosale.dto.UsuarioCreateDTO;
import com.pontosale.entity.Usuario;
import com.pontosale.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FaceService faceService;

    @Autowired
    private PontoService pontoService;

//    @Override
//    public List<Usuario> findAll() {
//        return
//    }

    @Override
    public void create(UsuarioCreateDTO usuarioCreateDTO, String email) {

        Usuario usuarioCriador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {

                    System.out.println("orElseThrow");

                    return new RuntimeException("Usuário criador não encontrado");
                });

        Usuario usuario = new Usuario();

        usuario.setNome(usuarioCreateDTO.getNome());
        usuario.setEmail(usuarioCreateDTO.getEmail());
        usuario.setSenha(usuarioCreateDTO.getSenha());
        usuario.setFoto(usuarioCreateDTO.getImage());

        usuario.setStatus(StatusUsuario.ATIVO);

        usuario.setFoto(usuarioCreateDTO.getImage());
        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setCriadoPor(usuarioCriador);
        usuario.setAlteradoEm(LocalDateTime.now());
        usuario.setCriadoPor(usuarioCriador);

        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setCriadoPor(usuarioCriador);

        usuarioRepository.save(usuario);

    }

    @Override
    public boolean logTimePoint(byte[] image, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        byte[] dbImage = usuario.getFoto();
;
        boolean validarFace = faceService.validarFace(dbImage, image);

        if (validarFace) {
            pontoService.registerPonto(usuario);
        }

        return validarFace;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

}
