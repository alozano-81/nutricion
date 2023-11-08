package com.salud.nutricion.respuestas;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {
    private Object obj;
    private HttpStatus status;

    private String username;
    private String password;
    private String email;
    private String token;
    private String type = "Bearer";
    private String id;
    private Set<String> roless;
    List<String> roles;
}
