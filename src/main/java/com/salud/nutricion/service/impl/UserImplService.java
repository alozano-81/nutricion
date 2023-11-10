package com.salud.nutricion.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // @Autowired
    // PasswordEncoder encoder;

    @Override
    public Respuesta crearNuevoUsuario(UserDTO body) {
        Respuesta obj = new Respuesta();

        if (userRepository.existsByUsername(body.getUsername())) {
            obj.setMensaje(new MessageResponse("Error: Email is already in use!"));
            return obj;
        }

        if (userRepository.existsByEmail(body.getEmail())) {
            obj.setMensaje(new MessageResponse("Error: Email is already in use!"));
            return obj;
        }

        // Create new user's account
        UserEntitieDocument user = new UserEntitieDocument();
        user.setUsername(body.getUsername());
        user.setEmail(body.getEmail());
        // user.setPassword(encoder.encode(body.getPassword()));

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
        return obj;
    }

}
