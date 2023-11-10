package com.salud.nutricion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginControler {
    @Autowired
    LoginService loginService;

    @PostMapping(value = "/validar-credenciales", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta> validarCredencialesAcceso(
            @RequestParam(value = "usuario", required = false) String usuario,
            @RequestParam(value = "password", required = false) String password) {
        Respuesta out = new Respuesta();
        try {
            out = loginService.validarCredenciales(usuario, password);
            if (out.getStatus().equals(HttpStatus.UNAUTHORIZED)) {
                throw new ResponseStatusException(out.getStatus());
            }
            return new ResponseEntity<>(out, out.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(out, out.getStatus());
        }

    }

}
