package com.salud.nutricion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salud.nutricion.dto.RegistroPacientesDTO;
import com.salud.nutricion.service.RegistroPacientesService;

@RestController
@RequestMapping("api/paciente")
public class ResgistroPacientes {

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

}
