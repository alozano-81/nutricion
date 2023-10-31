package com.salud.nutricion.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("login")
public class LoginEntitieDocument {
     @Id
    private String id;

    private String user;
    private String password;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

}
