package com.challenge.Foro.Kitty.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.challenge.Foro.Kitty.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String secret;
    public String generarToken(Usuario usuario){
       try {
           var algoritmo = Algorithm.HMAC256(secret);
           return JWT.create()
                   .withIssuer("API Kitty")
                   .withSubject(usuario.getLogin())
                   .withExpiresAt(fechaExpiracion())
                   .sign(algoritmo);
       } catch (JWTCreationException exception){
           throw new RuntimeException("Error al generar token", exception);
       }
    }
    public String getSubject(String token){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Kitty")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            return null;
        }
    }
    private Instant fechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
