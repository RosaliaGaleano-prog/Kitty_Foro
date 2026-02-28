package com.challenge.Foro.Kitty.dto;
import java.util.List;
import com.challenge.Foro.Kitty.model.Topico; // Por si usas la entidad en el constructor

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        List<DatosListadoRespuesta> respuestas
) {
    // Constructor compacto para convertir la Entidad Topico a este DTO
    public DatosDetalleTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                // Transformamos la lista de entidades Respuesta a lista de DTOs
                topico.getRespuestas().stream()
                        .map(DatosListadoRespuesta::new)
                        .toList()
        );
    }
}