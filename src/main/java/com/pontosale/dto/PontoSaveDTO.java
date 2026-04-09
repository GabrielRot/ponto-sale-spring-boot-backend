package com.pontosale.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PontoSaveDTO {

    private LocalDateTime dataHoraAbertura;

    private LocalDateTime dateHoraFechamento;

}
