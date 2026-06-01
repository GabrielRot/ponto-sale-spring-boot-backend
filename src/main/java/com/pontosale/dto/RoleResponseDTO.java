package com.pontosale.dto;

import com.pontosale.service.PermissoesRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleResponseDTO {

    private String nome;

    private String descricao;

    private List<PermissoesRole> permissoes;

}
