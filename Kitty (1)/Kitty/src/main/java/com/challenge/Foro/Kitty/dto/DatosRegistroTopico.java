package com.challenge.Foro.Kitty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotNull Long idUsuario,
        @NotBlank(message = "El mensaje no puede estar vacio")
        String mensaje,
        @NotBlank(message = "El nombre del curso es obligatorio")
        String nombreCurso,
        @NotBlank(message = "El titulo es obligatorio")
        String titulo
) {
}
