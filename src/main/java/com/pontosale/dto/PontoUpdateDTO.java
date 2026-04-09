package com.pontosale.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PontoUpdateDTO {

    Long id;

    LocalDateTime dataHoraAbertura;

    LocalDateTime dataHoraFechamento;

}
