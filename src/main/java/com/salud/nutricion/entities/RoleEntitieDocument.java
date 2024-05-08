package com.salud.nutricion.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("roles")
public class RoleEntitieDocument {
    @Id
    private String id;

    private String nombre;
    private String codigo;

}