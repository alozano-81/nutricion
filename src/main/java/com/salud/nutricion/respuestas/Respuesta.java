package com.salud.nutricion.respuestas;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Respuesta {
    private Object obj;
    private HttpStatus status;
    private String token;
}
