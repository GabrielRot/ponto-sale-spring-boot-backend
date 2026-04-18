package com.pontosale.dto;

import com.pontosale.service.TipoInsercaoPonto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PontosRegistradosResponseDTO {

    private Long id;

    private LocalDateTime dataHoraAbertura;

    private LocalDateTime dataHoraFechamento;

    private TipoInsercaoPonto tipoInsercaoPonto;

}
