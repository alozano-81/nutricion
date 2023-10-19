package com.salud.nutricion.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("datosPersonales")
public class DocumentRegistroPacientes {

    @Id
    private String id;

    private String nombres;

    private String apellidos;

    private Long documento;

    private String sexo;

    private String fechaNacimiento;

    private String estadoCivil;

    private String escolaridad;

    private String ocupacion;

    private String direccion;

    private String pais;

    private Long telefono;

    private String email;

}
