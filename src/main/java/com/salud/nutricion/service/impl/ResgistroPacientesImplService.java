package com.salud.nutricion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salud.nutricion.entities.DocumentRegistroPacientes;
import com.salud.nutricion.repository.RegistroPacientesRepository;
import com.salud.nutricion.service.RegistroPacientesService;


@Service
public class ResgistroPacientesImplService implements RegistroPacientesService {

    @Autowired
    RegistroPacientesRepository documentoRepository;

    @Override
    public DocumentRegistroPacientes getTodos(String name) {
        DocumentRegistroPacientes out = new DocumentRegistroPacientes();
        List<DocumentRegistroPacientes> list = documentoRepository.getTodos(name);
        System.out.println("VER: " + list);
        return out;
    }

}
