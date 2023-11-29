package com.salud.nutricion.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("pacientes")
public class DocumentRegistroPacientes {

    @Id
    private String id;

    private String nombres;

    private String apellidos;

    private Long documento;

    private String sexo;

    private LocalDateTime fechaNacimiento;

    private String estadoCivil;

    private String escolaridad;

    private String ocupacion;

    private String direccion;

    private String pais;

    private Long telefono;

    private String email;

}
