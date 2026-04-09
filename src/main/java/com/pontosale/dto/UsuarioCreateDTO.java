package com.pontosale.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDTO {

    private String nome;
    private String senha;
    private String email;
    private byte[] image;

}
