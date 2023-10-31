package com.salud.nutricion.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LoginDTO {
    private String user;
    private String password;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}
