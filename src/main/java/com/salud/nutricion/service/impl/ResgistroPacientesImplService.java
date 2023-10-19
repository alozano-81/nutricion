package com.salud.nutricion.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salud.nutricion.dto.RegistroPacientesDTO;
import com.salud.nutricion.entities.DocumentRegistroPacientes;
import com.salud.nutricion.repository.RegistroPacientesRepository;
import com.salud.nutricion.service.RegistroPacientesService;

@Service
public class ResgistroPacientesImplService implements RegistroPacientesService {

    @Autowired
    RegistroPacientesRepository documentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegistroPacientesDTO getTodos(String name) {
        RegistroPacientesDTO out = new RegistroPacientesDTO();
        List<DocumentRegistroPacientes> list = documentoRepository.getTodos(name);

        System.out.println("VER: " + list);

        for (DocumentRegistroPacientes p : list) {
            out = modelMapper.map(p, RegistroPacientesDTO.class);
        }
        System.out.println("VER2: " + out);
        return out;
    }

}
