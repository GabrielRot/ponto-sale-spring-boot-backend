package com.pontosale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Text;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "DESCRICAO", nullable = true)
    private String descricao;

    @Column(name = "CRIADO_EM", nullable = true)
    private LocalDateTime criadoEm;

    @ManyToOne
    @JoinColumn(name = "CRIADO_POR",  nullable = true)
    private Usuario criadoPor;

    @Column(name = "ALTERADO_EM", nullable = true)
    private LocalDateTime alteradoEm;

    @ManyToOne
    @JoinColumn(name = "ALTERADO_POR", nullable = true)
    private Usuario alteradoPor;

}
