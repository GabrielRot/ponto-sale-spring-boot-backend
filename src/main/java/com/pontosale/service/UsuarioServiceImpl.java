package com.pontosale.service;

import com.pontosale.dto.UsuarioCreateDTO;
import com.pontosale.entity.Role;
import com.pontosale.entity.RolePermission;
import com.pontosale.entity.Usuario;
import com.pontosale.entity.UsuarioRole;
import com.pontosale.repository.RolePermissionRepository;
import com.pontosale.repository.RoleRepository;
import com.pontosale.repository.UsuarioRepository;
import com.pontosale.repository.UsuarioRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioRoleRepository usuarioRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Autowired
    private FaceService faceService;

    @Autowired
    private PontoService pontoService;

//    @Override
//    public List<Usuario> findAll() {
//        return
//    }

    @Override
    public void createDefaultUsers() {
        if (!usuarioRepository.existsByEmail("admin@gmail.com")) {
            Usuario usuario = new Usuario();

            usuario.setNome("Admin");
            usuario.setEmail("admin@gmail.com");
            usuario.setSenha("123");
            usuario.setStatus(StatusUsuario.ATIVO);
            usuario.setCriadoEm(LocalDateTime.now());
            usuario.setAlteradoEm(LocalDateTime.now());

            usuarioRepository.save(usuario);

            Role role = new Role();

            role.setNome("Admin");
            role.setDescricao("Administrador do sistema com direito a tudo");
            role.setCriadoEm(LocalDateTime.now());
            role.setCriadoPor(usuario);

            roleRepository.save(role);

            for (PermissoesRole permissaoRole : PermissoesRole.values()) {
                RolePermission rolePermission = new RolePermission();

                rolePermission.setRole(role);
                rolePermission.setPermissaoRole(permissaoRole);

                rolePermissionRepository.save(rolePermission);
            }

            UsuarioRole usuarioRole = new UsuarioRole();

            usuarioRole.setUsuario(usuario);
            usuarioRole.setRole(role);

            usuarioRoleRepository.save(usuarioRole);

        }
    }

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

    @Override
    public Usuario findById(Long id) { return usuarioRepository.findById(id).get(); }
    
}
