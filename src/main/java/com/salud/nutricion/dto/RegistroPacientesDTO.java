package com.salud.nutricion.dto;

import lombok.Data;

@Data
public class RegistroPacientesDTO {
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
