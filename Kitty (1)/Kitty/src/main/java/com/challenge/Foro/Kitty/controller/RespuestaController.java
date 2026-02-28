package com.challenge.Foro.Kitty.controller;

import com.challenge.Foro.Kitty.dto.DatosActualizacionRespuesta;
import com.challenge.Foro.Kitty.dto.DatosListadoRespuesta;
import com.challenge.Foro.Kitty.dto.DatosRegistroRespuesta;
import com.challenge.Foro.Kitty.model.Respuesta;
import com.challenge.Foro.Kitty.repository.RespuestaRepository;
import com.challenge.Foro.Kitty.repository.TopicoRepository;
import com.challenge.Foro.Kitty.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository repository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroRespuesta datos) {
        var topico = topicoRepository.getReferenceById(datos.idTopico());
        var autor = usuarioRepository.getReferenceById(datos.idAutor());

        var respuesta = new Respuesta(datos.mensaje(), topico, autor);


        repository.save(respuesta);

        return ResponseEntity.ok("¡Respuesta gatuna enviada!");
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuesta>> listar(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        return ResponseEntity.ok(repository.findAll(paginacion).map(DatosListadoRespuesta::new));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionRespuesta datos) {
        var respuesta = repository.getReferenceById(id);
        respuesta.actualizarDatos(datos);
        return ResponseEntity.ok(new DatosListadoRespuesta(respuesta));
    }
}