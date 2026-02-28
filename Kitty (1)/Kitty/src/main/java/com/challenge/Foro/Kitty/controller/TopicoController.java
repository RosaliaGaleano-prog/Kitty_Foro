package com.challenge.Foro.Kitty.controller;

import com.challenge.Foro.Kitty.dto.DatosActualizarTopico;
import com.challenge.Foro.Kitty.dto.DatosDetalleTopico;
import com.challenge.Foro.Kitty.dto.DatosListadoTopico;
import com.challenge.Foro.Kitty.dto.DatosRegistroTopico;
import com.challenge.Foro.Kitty.model.Topico;
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
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos,
                                    UriComponentsBuilder uriComponentsBuilder) {
        // Buscamos el autor para asociarlo al tópico
        var autor = usuarioRepository.getReferenceById(datos.idUsuario());

        // Creamos el tópico con el constructor de la Entidad
        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, datos.nombreCurso());
        topicoRepository.save(topico);

        // Generamos el DTO de detalle (que ahora incluye la lista de respuestas vacía)
        var datosDetalleTopico = new DatosDetalleTopico(topico);

        // Retornamos 201 Created con la URL del nuevo recurso
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(datosDetalleTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalle(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        // Este DTO usa el constructor que creamos para mapear las respuestas
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizarTopico datos) {
        var topico = topicoRepository.getReferenceById(datos.id());
        topico.actualizarDatos(datos.titulo(), datos.mensaje()); // Asegúrate de tener este método en Topico.java

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }
}