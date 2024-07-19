package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Usuario;
import com.panaderia.gestor.util.LoggerConfig;

import java.util.Map;
import java.util.logging.Logger;

public class AuthService {
    private Map<String, Usuario> usuarios;
    private static final Logger logger = LoggerConfig.getLogger();

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario login(String username, String password) {
        if (usuarios.containsKey(username) && usuarios.get(username).getPassword().equals(password)) {
            logger.info("Usuario autenticado: " + username);
            return usuarios.get(username);
        }
        logger.warning("Falló la autenticación para el usuario: " + username);
        return null;
    }
}
