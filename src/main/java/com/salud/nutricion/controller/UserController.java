package com.salud.nutricion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.salud.nutricion.dto.UserDTO;
import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.service.UserService;

@RestController
@RequestMapping("/api/auth/")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("greetings")
    public String greetings(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("llega");
        return "Hello {" + name + "}";
    }

    public ResponseEntity<Respuesta> crearUsuarios(@RequestBody UserDTO body) {
        Respuesta out = new Respuesta();
        try {
            out = userService.crearNuevoUsuario(body);
            if (out.getStatus().equals(HttpStatus.UNAUTHORIZED)) {
                throw new ResponseStatusException(out.getStatus());
            }
            return new ResponseEntity<>(out, out.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(out, out.getStatus());
        }

    }

}
