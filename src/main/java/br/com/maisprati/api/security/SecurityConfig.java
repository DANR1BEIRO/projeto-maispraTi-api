package br.com.maisprati.api.security;

import br.com.maisprati.api.repository.UserRepository;
import br.com.maisprati.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // necessário para H2 console
                .headers().frameOptions().disable() // permite abrir o console em iframe
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/h2-console/**", "/api/**").permitAll() // libera o H2
                .anyRequest().authenticated()
                .and()
                .formLogin(); // login padrão do Spring Security

        return http.build();
    }


}

