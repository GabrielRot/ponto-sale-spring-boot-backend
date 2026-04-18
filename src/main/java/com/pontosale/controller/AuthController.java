package com.pontosale.controller;

import com.pontosale.dto.AuthDTO;
import com.pontosale.dto.AuthResponseDTO;
import com.pontosale.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) {
        try {
            AuthResponseDTO authResponseDTO = authService.login(authDTO);

            System.out.println("AuthToken: " + authResponseDTO.getToken());

            return ResponseEntity.ok(authResponseDTO);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
