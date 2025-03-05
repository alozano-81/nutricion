package com.salud.nutricion.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RegistroInfoPacientesDTO implements Serializable {

    private static final long serialVersionUID = 2843698612812435334L;

    private String id;
    private Long idPaciente;
    private String motivo;
    private List<Object> varios;
}
