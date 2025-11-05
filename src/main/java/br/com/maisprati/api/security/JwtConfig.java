package br.com.maisprati.api.security;

import br.com.maisprati.api.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class JwtConfig {

    @Bean
    @Profile("!test")
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtProvider jwtProvider, AuthService authService) {
        return new JwtAuthenticationFilter(jwtProvider, authService);
    }
}