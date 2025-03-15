package com.salud.nutricion.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AntecedentesFamiliaresDTO implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    private String name;
    private Boolean completed;

}
