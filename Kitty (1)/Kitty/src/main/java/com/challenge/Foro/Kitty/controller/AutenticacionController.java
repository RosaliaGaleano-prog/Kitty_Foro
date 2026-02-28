package com.challenge.Foro.Kitty.controller;

import com.challenge.Foro.Kitty.dto.DatosAutenticacionUsuario;
import com.challenge.Foro.Kitty.infra.DatosJWTToken;
import com.challenge.Foro.Kitty.infra.TokenService;
import com.challenge.Foro.Kitty.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.clave());
            var usuarioAutenticado = manager.authenticate(authToken);
            var principal = (Usuario) usuarioAutenticado.getPrincipal();
            var tokenJWT = tokenService.generarToken(principal);

            return ResponseEntity.ok(new DatosJWTToken(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
