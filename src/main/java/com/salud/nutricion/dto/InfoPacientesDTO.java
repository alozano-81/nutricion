package com.salud.nutricion.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class InfoPacientesDTO implements Serializable {

    private static final long serialVersionUID = 2843698612812435333L;

    List<ProblemasActualesDTO> problemasActuales = new ArrayList<>();
    List<AntecedentesFamiliaresDTO> antecedentesFamiliares = new ArrayList<>();
    List<TomaMedicamentosDTO> tomaMedicamentos = new ArrayList<>();
    FormularioIndicadoresDTO formularioIndicadoresClinicos;
}