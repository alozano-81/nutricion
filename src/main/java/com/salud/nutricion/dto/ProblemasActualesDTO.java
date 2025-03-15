package com.salud.nutricion.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProblemasActualesDTO implements Serializable {

    private static final long serialVersionUID = 2813698612812435332L;

    private String name;
    private Boolean completed;

}
