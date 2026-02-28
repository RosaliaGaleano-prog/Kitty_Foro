package com.challenge.Foro.Kitty.dto;

import com.challenge.Foro.Kitty.model.Respuesta;
import java.time.LocalDateTime;

public record DatosListadoRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean solucion,
        String autor,
        Long idTopico
) {
    public DatosListadoRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion(),
                respuesta.getAutor().getLogin(), // Mostramos el nombre, no el ID
                respuesta.getTopico().getId()
        );
    }
}