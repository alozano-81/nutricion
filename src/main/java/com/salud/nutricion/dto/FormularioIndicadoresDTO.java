package com.salud.nutricion.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FormularioIndicadoresDTO implements Serializable {

    private static final long serialVersionUID = 2803698612812435332L;

    private String id;
    private Long idPaciente;
    private String problemasactuales;
    private String deposicion;
    private String dentadura;
    private String otros;
    private String observaciones;
    private String enfermedad_diagnosticada;
    private String enfermedad_importante;
    private String medicamento;
    private String dosis;
    private String desdecuandodosis;
    private String toma;
    private String antecedente_familiares;
    private String cirujia;
    private String obecidad;
    private String diabetes;
    private String Obesidad;
    private String Diabetes;
    private String ta;
    private String cancer;
    private String hipercolesterolemia;
    private String hipertrigeceridemia;
    private String hipotiroidismo;

}
