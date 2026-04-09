package com.pontosale.entity;

import com.pontosale.service.TipoInsercaoPonto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ponto")
public class Ponto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @Column(name = "data_hora_abertura", nullable = false)
    private LocalDateTime dataHoraAbertura;

    @Column(name = "data_hora_fechamento", nullable = true)
    private LocalDateTime dataHoraFechamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_insercao", nullable = false)
    private TipoInsercaoPonto tipoInsercaoPonto;

}
