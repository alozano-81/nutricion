package com.salud.nutricion.service;

import com.salud.nutricion.dto.UserDTO;
import com.salud.nutricion.respuestas.Respuesta;

public interface UserService {
    public Respuesta getListaRoles();

    public Respuesta getAll();

    public Respuesta crearNuevoUsuario(UserDTO body);
}
