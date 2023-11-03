package com.salud.nutricion.service;

import java.util.List;

import com.salud.nutricion.dto.PaisesDTO;
import com.salud.nutricion.dto.RegistroPacientesDTO;
import com.salud.nutricion.entities.DocumentRegistroPacientes;
import com.salud.nutricion.respuestas.Respuesta;

public interface RegistroPacientesService {

    public RegistroPacientesDTO getByName(String name);

    public List<RegistroPacientesDTO> getAll();

    public List<PaisesDTO> getAllPaises();

    public Respuesta registrarPacientes(RegistroPacientesDTO formulario);

    public DocumentRegistroPacientes buscarByCedula(Long id);

}
