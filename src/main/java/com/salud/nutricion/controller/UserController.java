package com.salud.nutricion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.salud.nutricion.dto.UserDTO;
import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.security.jwt.JWTAuthorizationFilter;
import com.salud.nutricion.service.UserService;

@RestController
@RequestMapping("/api/auth/usersesion/")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JWTAuthorizationFilter jwtUtil;

    @GetMapping("validar-sesion-test")
    public ResponseEntity<Respuesta> greetings(@RequestParam(value = "name", defaultValue = "World") String name,
            @RequestParam(value = "token") String token) {
        Respuesta out = new Respuesta();
        try {
            out = jwtUtil.procesarToken(token);
            if (out.getStatus().equals(HttpStatus.UNAUTHORIZED)) {
                throw new ResponseStatusException(out.getStatus());
            }
            return new ResponseEntity<>(out, out.getStatus());
        } catch (Exception e) {
            System.out.println("VER ex general: " + e.getMessage());
            out.setStatus(HttpStatus.FOUND);
            return new ResponseEntity<>(out, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("validar-sesion")
    public ResponseEntity<Respuesta> validarSesion(@RequestHeader(value = "token", required = true) String token) {
        Respuesta out = new Respuesta();
        try {
            out = jwtUtil.procesarToken(token.contains("Bearer ") ? token.substring(7) : token);
            if (out.getStatus().equals(HttpStatus.UNAUTHORIZED)) {
                throw new ResponseStatusException(out.getStatus());
            }
            return new ResponseEntity<>(out, out.getStatus());
        } catch (Exception e) {
            System.out.println("VER ex general: " + e.getMessage());
            out.setStatus(HttpStatus.FOUND);
            return new ResponseEntity<>(out, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("creacion")
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
