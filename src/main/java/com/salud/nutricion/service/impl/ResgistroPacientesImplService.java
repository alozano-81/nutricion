package com.salud.nutricion.service.impl;

import java.util.ArrayList;
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
    public RegistroPacientesDTO getByName(String name) {
        RegistroPacientesDTO out = new RegistroPacientesDTO();
        List<DocumentRegistroPacientes> list = documentoRepository.getByName(name);

        for (DocumentRegistroPacientes p : list) {
            out = modelMapper.map(p, RegistroPacientesDTO.class);
        }
        System.out.println("VER2: " + out);
        return out;
    }

    @Override
    public List<RegistroPacientesDTO> getAll() {
        List<RegistroPacientesDTO> out = new ArrayList<>();
        List<DocumentRegistroPacientes> list = documentoRepository.getTodos();
        System.out.println("VER: " + list);
        for (DocumentRegistroPacientes p : list) {
            RegistroPacientesDTO rp = new RegistroPacientesDTO();
            rp = modelMapper.map(p, RegistroPacientesDTO.class);
            out.add(rp);
        }
        return out;
    }

}
