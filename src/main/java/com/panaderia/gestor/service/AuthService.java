package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, Usuario> usuarios = new HashMap<>();

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario login(String username, String password) {
        Usuario usuario = usuarios.get(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }
}
