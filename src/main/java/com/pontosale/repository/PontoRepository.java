package com.pontosale.repository;

import com.pontosale.entity.Ponto;
import com.pontosale.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PontoRepository extends JpaRepository<Ponto, Long> {

    List<Ponto> findAllByUsuario(Usuario usuario);

    Optional<Ponto> getByUsuarioAndDataHoraFechamentoIsEmpty(Usuario usuario);

}
