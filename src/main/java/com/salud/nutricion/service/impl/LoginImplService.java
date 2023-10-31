package com.salud.nutricion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salud.nutricion.dto.LoginDTO;
import com.salud.nutricion.entities.LoginEntitieDocument;
import com.salud.nutricion.repository.LoginRepository;
import com.salud.nutricion.service.LoginService;

@Service
public class LoginImplService implements LoginService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LoginRepository loginRepository;

    @Override
    public String validarCredenciales(String usuario, String password) {
        String out = "";
        try {
            LoginEntitieDocument obj = loginRepository.getLogin(usuario, password);
            System.out.println("=====>  " + obj);
            if (obj != null) {
                LoginDTO objDto = modelMapper.map(obj, LoginDTO.class);
                out = "ok";
            } else {
                out = "error";
            }
        } catch (Exception e) {
            out = "error";
        }

        return out;
    }

}
