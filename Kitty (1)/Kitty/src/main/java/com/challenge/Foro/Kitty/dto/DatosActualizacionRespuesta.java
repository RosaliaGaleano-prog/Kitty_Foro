package com.challenge.Foro.Kitty.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizacionRespuesta(
        @NotBlank String mensaje
) {
}