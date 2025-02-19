package com.salud.nutricion.dto;

import lombok.Data;

@Data
public class RegistroInfoPacientesDTO {
    private String id;
    private Long idPaciente;
    private String motivo;
    private String varios;
}
