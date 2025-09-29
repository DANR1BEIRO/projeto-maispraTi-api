package br.com.maisprati.api.security;

import br.com.maisprati.api.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys; // Import novo e importante
import jakarta.annotation.PostConstruct; // Import necessário
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Gera um token JWT para o usuário informado.
     *
     * @param user usuário autenticado
     * @return token JWT válido
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.key)
                .compact();
    }
}