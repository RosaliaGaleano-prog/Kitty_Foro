package com.challenge.Foro.Kitty.dto;

import com.challenge.Foro.Kitty.model.Usuario;

public record DatosListaUsuario(Long id, String login) {
    public DatosListaUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getLogin());
    }
}
