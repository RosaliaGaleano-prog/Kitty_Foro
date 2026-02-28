package com.challenge.Foro.Kitty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatodRegitroUsuario(
        @NotBlank String login,
        @NotBlank @Size(min = 6) String clave
) {
}
