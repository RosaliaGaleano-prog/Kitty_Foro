package com.challenge.Foro.Kitty.dto;

import com.challenge.Foro.Kitty.model.Topico;
import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String autor
) {
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getAutor() != null ? topico.getAutor().getLogin() : "Autor no asignado"
        );
    }
}