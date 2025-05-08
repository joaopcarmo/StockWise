package com.stockwise.app.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String JWT_SECRET = "chave-super-segura"; // você pode armazenar em application.properties
    private final long JWT_EXPIRATION_MS = 86400000; // 1 dia em milissegundos

    public String generateToken(Authentication authentication) {
        String email = authentication.getName(); // o email vem do UserDetails
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Assinatura inválida do JWT");
        } catch (MalformedJwtException ex) {
            System.out.println("Token JWT malformado");
        } catch (ExpiredJwtException ex) {
            System.out.println("Token JWT expirado");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Token JWT não suportado");
        } catch (IllegalArgumentException ex) {
            System.out.println("Claims do JWT estão vazios");
        }
        return false;
    }
}