package com.pontosale.config;

import com.pontosale.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataInitializer {

    @Bean
    @Profile("dev")
    CommandLineRunner initDevUser(UsuarioService usuarioService){
        return args -> {
            usuarioService.createDefaultUsers();
        };
    }

}
