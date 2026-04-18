package com.pontosale.service;

import com.pontosale.dto.PontoSaveDTO;
import com.pontosale.dto.PontoUpdateDTO;
import com.pontosale.entity.Ponto;
import com.pontosale.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface PontoService {

    public List<Ponto> getAllPontoByUser(Usuario usuario);

    public Ponto getPontoByIdAndUsuario(Long id, Usuario usuario);

    public Optional<Ponto> registerPonto(Usuario usuario);

    public Ponto savePonto(PontoSaveDTO pontoSaveDTO, Usuario usuario);

    public Optional<Ponto> updatePonto(PontoUpdateDTO pontoUpdateDTO, Usuario usuario);

    public void deletePonto(Long id);

}
