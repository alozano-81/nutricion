package com.salud.nutricion.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("infoPaciente")
public class RegistroInfoPacientesDocument {

    @Id
    private String id;

    private Long idPaciente;

    private String motivo;

    private String varios;

}
