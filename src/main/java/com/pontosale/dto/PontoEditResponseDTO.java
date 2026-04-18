package com.pontosale.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PontoEditResponseDTO {

    private Long id;

    private LocalDateTime dataHoraAbertura;

    private LocalDateTime dataHoraFechamento;

    private String nomeUsuario;

    private byte[] fotoUsuario;

}
