package com.salud.nutricion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.salud.nutricion.dto.EstadosCivilDTO;
import com.salud.nutricion.dto.PaisesDTO;
import com.salud.nutricion.dto.RegistroPacientesDTO;
import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.service.RegistroPacientesService;

@RestController
@RequestMapping("api/auth/paciente")
public class PacientesController {

    @Autowired
    RegistroPacientesService registroPacientesService;

    @GetMapping("/get-datos-personales-by-name")
    public ResponseEntity<RegistroPacientesDTO> pruebaNavi() {
        RegistroPacientesDTO out = new RegistroPacientesDTO();
        out = registroPacientesService.getByName("Camilo");
        System.out.println("llega");

        // return new ResponseEntity<>("OKK", HttpStatus.ACCEPTED);
        return new ResponseEntity<>(out, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-datos-personales")
    public ResponseEntity<List<RegistroPacientesDTO>> getAll() {
        List<RegistroPacientesDTO> out = new ArrayList<>();
        out = registroPacientesService.getAll();
        System.out.println("llega");

        // return new ResponseEntity<>("OKK", HttpStatus.ACCEPTED);
        return new ResponseEntity<>(out, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-paises")
    public ResponseEntity<List<PaisesDTO>> getAllPaises() {
        List<PaisesDTO> out = new ArrayList<>();
        out = registroPacientesService.getAllPaises();
        System.out.println("llega");

        // return new ResponseEntity<>("OKK", HttpStatus.ACCEPTED);
        return new ResponseEntity<>(out, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-estados-civil")
    public ResponseEntity<List<EstadosCivilDTO>> getAllEstadoscivil() {
        List<EstadosCivilDTO> out = new ArrayList<>();
        out = registroPacientesService.getAllCiviles();
        return new ResponseEntity<>(out, HttpStatus.ACCEPTED);
    }

    /**
     * Registrar pacientes
     */
    @PostMapping(value = "/registrar")
    public ResponseEntity<Respuesta> registrarPacientes(@RequestBody RegistroPacientesDTO formularioRegistro,
            @RequestHeader(value = "Authorization", required = true) String token) {
        Respuesta out = new Respuesta();
        try {
            System.out.println("ver body" + formularioRegistro);
            out = registroPacientesService.registrarPacientes(formularioRegistro, false);
            if (out.getStatus().equals(HttpStatus.BAD_REQUEST)) {
                throw new ResponseStatusException(out.getStatus());
            }
            return new ResponseEntity<>(out, out.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(out, out.getStatus());
        }
    }

    @PutMapping(value = "/actualizar")
    public ResponseEntity<Respuesta> actualizarPacientes(@RequestBody RegistroPacientesDTO formularioRegistro,
            @RequestHeader(value = "Authorization", required = true) String token) {
        Respuesta out = new Respuesta();
        try {
            System.out.println("ver body actualizar" + formularioRegistro);
            out = registroPacientesService.registrarPacientes(formularioRegistro, true);
            if (out.getStatus().equals(HttpStatus.BAD_REQUEST)) {
                throw new ResponseStatusException(out.getStatus());
            }
            return new ResponseEntity<>(out, out.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(out, out.getStatus());
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Respuesta> deletePaciente(
            @RequestHeader(value = "Authorization", required = true) String token,
            @RequestBody RegistroPacientesDTO formularioRegistro) {
        Respuesta out = new Respuesta();
        try {
            out = registroPacientesService.deletePaciente(formularioRegistro);
            if (out.getStatus().equals(HttpStatus.BAD_REQUEST)) {
                throw new ResponseStatusException(out.getStatus());
            }
            return new ResponseEntity<>(out, out.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(out, out.getStatus());
        }
    }

}
