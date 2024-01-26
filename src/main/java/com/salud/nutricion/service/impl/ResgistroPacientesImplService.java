package com.salud.nutricion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.salud.nutricion.respuestas.MessageResponse;
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
    public Respuesta registrarPacientes(RegistroPacientesDTO formulario, boolean tipoRegistro) {
        Respuesta out = new Respuesta();
        try {
            DocumentRegistroPacientes obj = new DocumentRegistroPacientes();
            obj = modelMapper.map(formulario, DocumentRegistroPacientes.class);

            DocumentRegistroPacientes buscarUnico = buscarByCedula(obj.getDocumento(), obj.getId());
            if (buscarUnico == null && tipoRegistro) {
                DocumentRegistroPacientes respuesta = documentoRepository.save(obj);
                if (respuesta != null) {
                    out.setStatus(HttpStatus.ACCEPTED);
                    out.setObj(respuesta);
                } else {
                    out.setMensaje(new MessageResponse("Error: al momento de ser creado"));
                    out.setStatus(HttpStatus.BAD_REQUEST);
                    out.setObj(respuesta);
                }

            } else {
                if (tipoRegistro) {
                    Optional<DocumentRegistroPacientes> verifica = documentoRepository.getById(obj.getDocumento());
                    // obj.setId(verifica.get().getId());
                    DocumentRegistroPacientes respuesta = null;
                    if (verifica.isPresent()) {
                        respuesta = documentoRepository.save(obj);
                        // System.out.println("===> " + verifica.get().se);
                        out.setMensaje(new MessageResponse("ok: Paciente actualizado correctamente!"));
                        out.setStatus(HttpStatus.ACCEPTED);
                        out.setObj(respuesta);
                    } else {
                        out.setMensaje(new MessageResponse("Error: No es posible actualizar el documento!"));
                        out.setStatus(HttpStatus.CONFLICT);
                        out.setObj(respuesta);
                    }

                } else {
                    out.setMensaje(new MessageResponse("Error: No es posible modificar el documento!"));
                    out.setStatus(HttpStatus.FOUND);
                    out.setObj(buscarUnico);
                }

            }
            System.out.println("ver rrr: " + out);
        } catch (Exception e) {
            out.setMensaje(new MessageResponse("Error: " + e.getMessage()));
            out.setStatus(HttpStatus.BAD_REQUEST);
            out.setObj(formulario);
        }
        return out;
    }

    @Override
    public DocumentRegistroPacientes buscarByCedula(Long documento, String id) {
        DocumentRegistroPacientes out = null;
        try {
            Optional<DocumentRegistroPacientes> out2 = documentoRepository.getById(documento, id);
            System.out.println("Salida: " + out2);
            if (out2.isPresent()) {
                out = out2.get();
            }
            return out;
        } catch (Exception e) {
            // TODO: handle exception
            return out;
        }
    }

    @Override
    public Respuesta deletePaciente(RegistroPacientesDTO formulario) {
        Respuesta out = new Respuesta();
        try {
            DocumentRegistroPacientes respuesta = null;
            DocumentRegistroPacientes obj = new DocumentRegistroPacientes();
            obj = modelMapper.map(formulario, DocumentRegistroPacientes.class);
            Optional<DocumentRegistroPacientes> searchObj = documentoRepository.findById(obj.getId());
            if (searchObj.isPresent()) {
                documentoRepository.delete(obj);
                out.setMensaje(new MessageResponse("ok: Paciente eliminado correctamente!"));
                out.setStatus(HttpStatus.ACCEPTED);
                out.setObj(respuesta);
            } else {
                out.setMensaje(new MessageResponse("Error: No es posible eliminar el documento!"));
                out.setStatus(HttpStatus.CONFLICT);
                out.setObj(respuesta);
            }
        } catch (Exception e) {
            out.setMensaje(new MessageResponse("Error: " + e.getMessage()));
            out.setStatus(HttpStatus.BAD_REQUEST);
            out.setObj(formulario);
        }

        return out;
    }

}
