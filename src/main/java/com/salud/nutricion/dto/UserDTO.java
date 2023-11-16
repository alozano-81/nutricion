package com.salud.nutricion.dto;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.salud.nutricion.entities.RoleEntitieDocument;

import lombok.Data;

@Data
public class UserDTO {
    private Object obj;
    private HttpStatus status;

    private String username;
    private String password;
    private String email;
    private String token;
    private String type = "Bearer";
    private String id;
    private Set<String> roless;
    Set<RoleEntitieDocument> idRol;
    List<String> roles;
}
