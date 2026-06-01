package com.pontosale.service;

import com.pontosale.dto.PontoSaveDTO;
import com.pontosale.dto.PontoUpdateDTO;
import com.pontosale.dto.RelatorioPontoDTO;
import com.pontosale.entity.Ponto;
import com.pontosale.entity.Usuario;
import com.pontosale.repository.PontoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PontoServiceImpl implements PontoService {

    @Autowired
    PontoRepository pontoRepository;

    @Override
    public List<Ponto> getAllPontoByUser(Usuario usuario) {
        return pontoRepository.findAllByUsuarioAndDataHoraFechamentoIsNotNull(usuario);
    }

    @Override
    public Ponto getPontoByIdAndUsuario(Long id, Usuario usuario) {
        return pontoRepository.findByIdAndUsuario(id, usuario);
    }


    @Override
    public Optional<Ponto> registerPonto(Usuario usuario) {
        Optional<Ponto> ponto = pontoRepository.getByUsuarioAndDataHoraFechamentoIsNull(usuario);

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
    
    @Override
    public byte[] gerarRelatorioPontoByUsuario(Usuario usuario) throws Exception {
        
        
        List<Ponto> pontos = pontoRepository.findAllByUsuarioAndDataHoraFechamentoIsNotNullOrderByDataHoraAberturaAsc(usuario);
        
        if (pontos.isEmpty()) {
            return null;
        }
        
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        List<RelatorioPontoDTO> dados =  pontos.stream().map(ponto -> {
            RelatorioPontoDTO relatorioPontoDTO = new RelatorioPontoDTO();

            relatorioPontoDTO.setData(ponto.getDataHoraAbertura().format(formatterData));
            relatorioPontoDTO.setEntrada(ponto.getDataHoraAbertura().format(formatterHora));
            relatorioPontoDTO.setSaida(ponto.getDataHoraFechamento().format(formatterHora));

            return relatorioPontoDTO;
        }).toList();

//        InputStream inputStream = getClass()
//                .getResourceAsStream(
//                        "/reports/relatorio_pontos_mensal.jrxml"
//                );
//
//        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        InputStream inputStream = getClass()
                .getResourceAsStream(
                        "/reports/relatorio_pontos_mensal.jasper"
                );

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream); 

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);

        Map<String, Object> parametros = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
        
        return JasperExportManager.exportReportToPdf(jasperPrint);
        
    }

}
