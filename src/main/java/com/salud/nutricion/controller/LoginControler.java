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

import com.salud.nutricion.service.LoginService;

@RestController
@RequestMapping("api/login")
public class LoginControler {
    @Autowired
    LoginService loginService;

    @PostMapping(value = "validar-credenciales", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> validarCredencialesAcceso(
            @RequestParam(value = "usuario", required = false) String usuario,
            @RequestParam(value = "password", required = false) String password) {
        String out = "";
        try {
            System.out.println("=====>  " + usuario);
            out = loginService.validarCredenciales(usuario, password);
            System.out.println("===out==>  " + out);
            if (out.equals("error")) {
                System.out.println("mostrar error: " + out);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, out);
            }
            System.out.println("llega ytr");
            return new ResponseEntity<>(out, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>(out, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
