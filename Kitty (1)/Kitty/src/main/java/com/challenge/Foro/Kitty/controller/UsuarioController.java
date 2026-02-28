package com.challenge.Foro.Kitty.controller;

import com.challenge.Foro.Kitty.dto.DatosAutenticacionUsuario;
import com.challenge.Foro.Kitty.dto.DatosListaUsuario;
import com.challenge.Foro.Kitty.infra.DatosJWTToken;
import com.challenge.Foro.Kitty.infra.TokenService;
import com.challenge.Foro.Kitty.model.Usuario;
import com.challenge.Foro.Kitty.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping
    public ResponseEntity<List<DatosListaUsuario>> listar() {
        List<DatosListaUsuario> listado = repository.findAll().stream()
                .map(DatosListaUsuario::new)
                .toList();

        return ResponseEntity.ok(listado);
    }
    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        String claveEncriptada = passwordEncoder.encode(datos.clave());
        Usuario usuario = new Usuario(datos.login(), claveEncriptada);
        repository.save(usuario);
        return ResponseEntity.ok().build();
    }
}
