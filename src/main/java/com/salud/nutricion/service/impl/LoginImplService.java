package com.salud.nutricion.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salud.nutricion.dto.UserDTO;
import com.salud.nutricion.entities.UserEntitieDocument;
import com.salud.nutricion.repository.LoginRepository;
import com.salud.nutricion.repository.UserRepository;
import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.security.jwt.JWTAuthtenticationConfig;
import com.salud.nutricion.service.LoginService;

@Service
public class LoginImplService implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginImplService.class);

    @Autowired
    JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Respuesta validarCredenciales(String usuario, String password) {
        Respuesta out = new Respuesta();
        try {
            Optional<UserEntitieDocument> pasEncode = userRepository.findByUsername(usuario);
            if (pasEncode.isPresent()) {
                if (encoder.matches(password, pasEncode.get().getPassword())) {
                    UserEntitieDocument obj = userRepository.getLogin(usuario, pasEncode.get().getPassword());
                    if (obj != null) {
                        UserDTO objDto = modelMapper.map(obj, UserDTO.class);
                        out.setObj(objDto.getIdRol());
                        String token = jwtAuthtenticationConfig.getJWTToken(usuario,
                                pasEncode.get().getIdRol().iterator().next().getNombre());
                        out.setToken(token);
                        out.setStatus(HttpStatus.ACCEPTED);
                    } else {
                        out.setStatus(HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    out.setStatus(HttpStatus.CONFLICT);
                    out.setMsn("La contraseña no es valida");
                }
            } else {
                out.setStatus(HttpStatus.CONFLICT);
                out.setMsn("Usuario no es correcto");
            }

        } catch (Exception e) {
            logger.error("VER MSN ERROR: ", e.getMessage());
            out.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return out;
    }

}
