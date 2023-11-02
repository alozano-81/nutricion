package com.salud.nutricion.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("pais")
public class PaisEntitieDocument {
    @Id
    private String id;
    private Long codigo;
    private String nombre;

}
