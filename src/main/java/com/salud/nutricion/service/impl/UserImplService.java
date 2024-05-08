package com.salud.nutricion.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salud.nutricion.dto.RolesDTO;
import com.salud.nutricion.dto.UserDTO;
import com.salud.nutricion.entities.ERole;
import com.salud.nutricion.entities.RoleEntitieDocument;
import com.salud.nutricion.entities.UserEntitieDocument;
import com.salud.nutricion.repository.RoleRepository;
import com.salud.nutricion.repository.UserRepository;
import com.salud.nutricion.respuestas.MessageResponse;
import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.service.UserService;

@Service
public class UserImplService implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Respuesta crearNuevoUsuario(UserDTO body) {
        Respuesta obj = new Respuesta();
        System.out.println("BODY: " + body);

        if (userRepository.existsByUsernameAndPassword(body.getUsername(), body.getPassword())) {
            obj.setMensaje(new MessageResponse("Error: Usuario y password existente!"));
            obj.setStatus(HttpStatus.FOUND);
        }

        if (userRepository.existsByUsername(body.getUsername())) {
            obj.setMensaje(new MessageResponse("Error: Usuario existente!"));
            obj.setStatus(HttpStatus.FOUND);
            return obj;
        }

        if (userRepository.existsByEmail(body.getEmail())) {
            obj.setMensaje(new MessageResponse("Error: Email existente!"));
            obj.setStatus(HttpStatus.FOUND);
            return obj;
        }

        // Create new user's account
        UserEntitieDocument user = new UserEntitieDocument();
        user.setUsername(body.getUsername());
        user.setEmail(body.getEmail());
        user.setPassword(encoder.encode(body.getPassword()));

        Set<String> strRoles = body.getRoless();
        Set<RoleEntitieDocument> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntitieDocument userRole = roleRepository.findByNombre(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntitieDocument adminRole = roleRepository.findByNombre(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        RoleEntitieDocument modRole = roleRepository.findByNombre(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        RoleEntitieDocument userRole = roleRepository.findByNombre(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setIdRol(roles);
        userRepository.save(user);
        obj.setStatus(HttpStatus.ACCEPTED);
        return obj;
    }

    @Override
    public Respuesta getListaRoles() {
        Respuesta out = new Respuesta();
        List<RolesDTO> lista = new ArrayList<>();
        List<RoleEntitieDocument> listaRoles = roleRepository.findAll();
        for (RoleEntitieDocument r : listaRoles) {
            RolesDTO rol = new RolesDTO();
            rol = modelMapper.map(r, RolesDTO.class);
            lista.add(rol);
        }
        out.setObj(lista);
        out.setStatus(HttpStatus.ACCEPTED);
        return out;
    }

    @Override
    public Respuesta getAll() {
        Respuesta out = new Respuesta();
        try {
            List<UserDTO> lista = new ArrayList<>();
            List<UserEntitieDocument> obj = userRepository.getAll();
            for (UserEntitieDocument l : obj) {
                UserDTO userDto = new UserDTO();
                userDto = modelMapper.map(l, UserDTO.class);
                lista.add(userDto);
            }
            out.setObj(lista);
            out.setStatus(HttpStatus.ACCEPTED);
            return out;
        } catch (Exception e) {
            out.setObj(null);
            out.setStatus(HttpStatus.BAD_REQUEST);
            return out;
        }
    }

}
