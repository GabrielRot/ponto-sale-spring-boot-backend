package com.pontosale.service;

import com.pontosale.dto.AuthDTO;
import com.pontosale.dto.AuthResponseDTO;

public interface AuthService {

    public AuthResponseDTO login(AuthDTO authDTO);

    private void validatePassword(AuthDTO authDTO, String senha) {

    }

}
