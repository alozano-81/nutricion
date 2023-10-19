package com.salud.nutricion.service;

import java.util.List;

import com.salud.nutricion.dto.RegistroPacientesDTO;

public interface RegistroPacientesService {

    public RegistroPacientesDTO getByName(String name);

    public List<RegistroPacientesDTO> getAll();

}
