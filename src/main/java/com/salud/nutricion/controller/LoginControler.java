package com.salud.nutricion.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.salud.nutricion.dto.LoginDTO;
import com.salud.nutricion.entities.ERole;
import com.salud.nutricion.entities.RoleEntitieDocument;
import com.salud.nutricion.entities.UserEntitieDocument;
import com.salud.nutricion.repository.RoleRepository;
import com.salud.nutricion.repository.UserRepository;
import com.salud.nutricion.respuestas.MessageResponse;
import com.salud.nutricion.respuestas.Respuesta;
import com.salud.nutricion.security.jwt.JwtUtils;
import com.salud.nutricion.service.LoginService;
import com.salud.nutricion.service.impl.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth/login")
public class LoginControler {
    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping(value = "validar-credenciales", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Respuesta> validarCredencialesAcceso(
            @RequestParam(value = "usuario", required = false) String usuario,
            @RequestParam(value = "password", required = false) String password) {
        Respuesta out = new Respuesta();
        try {
            out = loginService.validarCredenciales(usuario, password);
            if (out.getStatus().equals(HttpStatus.UNAUTHORIZED)) {
                throw new ResponseStatusException(out.getStatus());
            }
            return new ResponseEntity<>(out, out.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(out, out.getStatus());
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<Respuesta> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUser(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        Respuesta responses = new Respuesta();
        responses.setToken(jwt);
        responses.setId(userDetails.getId());
        responses.setRoles(roles);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Respuesta signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserEntitieDocument user = new UserEntitieDocument();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoless();
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

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
