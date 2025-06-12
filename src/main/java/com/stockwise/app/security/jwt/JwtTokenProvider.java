package com.stockwise.app.security.jwt;

import com.stockwise.app.model.UserModel;
import com.stockwise.app.dto.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "minhaChaveSuperSecretaComPeloMenos256Bits123456789012345678901234"; // >= 32 caracteres
    private final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    // Retorna a chave usada para assinar os tokens
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Gera um token JWT contendo o e-mail e a flag de admin
    public String generateToken(UserModel user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("admin", user.isAdmin())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateTokenFromUserDto(UserDto userDto) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userDto.getEmail())  // Usa o e-mail do UserDto
                .claim("admin", userDto.isAdmin())  // Usa a flag "admin" do UserDto
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    // Recupera o e-mail do token
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // Recupera o valor da flag "admin" do token
    public Boolean getIsAdminFromToken(String token) {
        return getClaims(token).get("admin", Boolean.class);
    }

    // Verifica se o token é válido (assinatura e expiração)
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    // Retorna os claims do token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}