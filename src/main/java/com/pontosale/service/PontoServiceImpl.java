package com.pontosale.service;

import com.pontosale.dto.PontoSaveDTO;
import com.pontosale.dto.PontoUpdateDTO;
import com.pontosale.entity.Ponto;
import com.pontosale.entity.Usuario;
import com.pontosale.repository.PontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PontoServiceImpl implements PontoService {

    @Autowired
    PontoRepository pontoRepository;

    @Override
    public List<Ponto> getPontos(Usuario usuario) {
        return pontoRepository.findAllByUsuario(usuario);
    }

    @Override
    public Optional<Ponto> registerPonto(Usuario usuario) {
        Optional<Ponto> ponto = pontoRepository.getByUsuarioAndDataHoraFechamentoIsEmpty(usuario);

        Ponto pontoSave;

        if (ponto.isPresent()) {
            pontoSave = ponto.get();

            pontoSave.setDataHoraFechamento(LocalDateTime.now());
            pontoSave.setTipoInsercaoPonto(TipoInsercaoPonto.AUTOMATICO);

            pontoRepository.save(pontoSave);
        } else {
            pontoSave = new Ponto();

            pontoSave.setUsuario(usuario);
            pontoSave.setDataHoraAbertura(LocalDateTime.now());
            pontoSave.setTipoInsercaoPonto(TipoInsercaoPonto.AUTOMATICO);

            pontoRepository.save(pontoSave);
        }

        return Optional.of(pontoSave);
    }

    @Override
    public Ponto savePonto(PontoSaveDTO pontoSaveDTO, Usuario usuario) {
        Ponto ponto = new Ponto();

        LocalDateTime dataHoraAbertura = pontoSaveDTO.getDataHoraAbertura();
        LocalDateTime dataHoraFechamento = pontoSaveDTO.getDateHoraFechamento();

        if (dataHoraAbertura == null || dataHoraFechamento == null) {
            return null;
        }

        if (dataHoraFechamento.isBefore(dataHoraAbertura)) {
            return null;
        }

        ponto.setUsuario(usuario);
        ponto.setDataHoraAbertura(dataHoraAbertura);
        ponto.setDataHoraFechamento(dataHoraFechamento);
        ponto.setTipoInsercaoPonto(TipoInsercaoPonto.MANUAL);

        pontoRepository.save(ponto);

        return ponto;
    }

    @Override
    public Optional<Ponto> updatePonto(PontoUpdateDTO pontoUpdateDTO, Usuario usuario) {
        Ponto ponto = pontoRepository.findById(pontoUpdateDTO.getId()).get();

        if (!ponto.getUsuario().equals(usuario)) {
            return null;
        }

        if (ponto == null) {
            return null;
        }

        LocalDateTime dataHoraAbertura = pontoUpdateDTO.getDataHoraAbertura();
        LocalDateTime dataHoraFechamento = pontoUpdateDTO.getDataHoraFechamento();

        if (dataHoraAbertura == null || dataHoraFechamento == null) {
            return null;
        }

        if (dataHoraFechamento.isBefore(dataHoraAbertura)) {
            return null;
        }

        ponto.setUsuario(usuario);
        ponto.setDataHoraAbertura(dataHoraAbertura);
        ponto.setDataHoraFechamento(dataHoraFechamento);
        ponto.setTipoInsercaoPonto(TipoInsercaoPonto.MANUAL);

        pontoRepository.save(ponto);

        return Optional.of(ponto);
    }

    @Override
    public void deletePonto(Long id) {
        pontoRepository.deleteById(id);
    }

}
