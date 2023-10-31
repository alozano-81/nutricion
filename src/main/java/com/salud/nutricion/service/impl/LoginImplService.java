package com.salud.nutricion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.salud.nutricion.dto.LoginDTO;
import com.salud.nutricion.entities.LoginEntitieDocument;
import com.salud.nutricion.repository.LoginRepository;
import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.service.LoginService;

@Service
public class LoginImplService implements LoginService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LoginRepository loginRepository;

    @Override
    public Respuesta validarCredenciales(String usuario, String password) {
        Respuesta out = new Respuesta();
        try {
            LoginEntitieDocument obj = loginRepository.getLogin(usuario, password);
            System.out.println("=====>  " + obj);
            if (obj != null) {
                LoginDTO objDto = modelMapper.map(obj, LoginDTO.class);
                // out.setObj(objDto);
                out.setStatus(HttpStatus.ACCEPTED);
            } else {
                out.setStatus(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            out.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return out;
    }

}
