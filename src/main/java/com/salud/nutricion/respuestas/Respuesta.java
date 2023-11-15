package com.salud.nutricion.respuestas;

import org.springframework.http.HttpStatus;

import io.jsonwebtoken.Claims;
import lombok.Data;

@Data
public class Respuesta {
    private Object obj;
    private HttpStatus status;
    private String token;
    private String msn;
    private MessageResponse mensaje;
    private Claims clains;
}
