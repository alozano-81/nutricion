package com.salud.nutricion.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class TomaMedicamentosDTO implements Serializable {

    private static final long serialVersionUID = 2823698612812435332L;

    private String name;
    private Boolean completed;

}
