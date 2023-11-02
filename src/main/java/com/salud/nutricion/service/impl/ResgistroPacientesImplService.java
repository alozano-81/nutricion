package com.salud.nutricion.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.salud.nutricion.dto.PaisesDTO;
import com.salud.nutricion.dto.RegistroPacientesDTO;
import com.salud.nutricion.entities.DocumentRegistroPacientes;
import com.salud.nutricion.entities.PaisEntitieDocument;
import com.salud.nutricion.repository.PaisesRepository;
import com.salud.nutricion.repository.RegistroPacientesRepository;
import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.service.RegistroPacientesService;

@Service
public class ResgistroPacientesImplService implements RegistroPacientesService {

    @Autowired
    RegistroPacientesRepository documentoRepository;

    @Autowired
    PaisesRepository paisesRepository;

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

    @Override
    public List<PaisesDTO> getAllPaises() {
        List<PaisesDTO> out = new ArrayList<>();
        List<PaisEntitieDocument> list = paisesRepository.getTodos();
        System.out.println("VER: " + list);
        for (PaisEntitieDocument p : list) {
            PaisesDTO rp = new PaisesDTO();
            rp = modelMapper.map(p, PaisesDTO.class);
            out.add(rp);
        }
        return out;
    }

    @Override
    public Respuesta registrarPacientes(RegistroPacientesDTO formulario) {
        Respuesta out = new Respuesta();
        DocumentRegistroPacientes obj = new DocumentRegistroPacientes();
        obj = modelMapper.map(formulario, DocumentRegistroPacientes.class);
        DocumentRegistroPacientes respuesta = documentoRepository.save(obj);
        if (respuesta != null) {
            out.setStatus(HttpStatus.ACCEPTED);
            out.setObj(respuesta);
        } else {
            out.setStatus(HttpStatus.BAD_REQUEST);
            out.setObj(respuesta);
        }
        System.out.println("ver rrr: " + respuesta);
        return out;
    }

}
