package com.challenge.Foro.Kitty.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class TrataDeErrores {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity trataError400(DataIntegrityViolationException e){
        return ResponseEntity.badRequest().body("El usuario ya se encuentra registrado.");
    }
}
