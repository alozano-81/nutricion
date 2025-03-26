package com.salud.nutricion.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.salud.nutricion.dto.InfoPacientesDTO;

import lombok.Data;

@Data
@Document("infoPaciente")
public class RegistroInfoPacientesDocument {

    @Id
    private String id;

    private Long idPaciente;

    private String motivo;

    private List<InfoPacientesDTO> varios;

}
