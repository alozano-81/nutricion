package com.salud.nutricion.service;

import com.salud.nutricion.respuestas.Respuesta;

public interface LoginService {
    public Respuesta validarCredenciales(String usuario, String password);
}
