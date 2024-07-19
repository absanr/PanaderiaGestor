package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Zona;
import com.panaderia.gestor.util.LoggerConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GestorZonas {
    private Map<Integer, Zona> zonas = new HashMap<>();
    private static final Logger logger = LoggerConfig.getLogger();

    public Map<Integer, Zona> getZonas() {
        return zonas;
    }

    public void setZonas(Map<Integer, Zona> zonas) {
        this.zonas = zonas;
    }

    public Zona getZonaPorId(int id) {
        return zonas.get(id);
    }

    public void agregarZona(Zona zona) {
        zonas.put(zona.getId(), zona);
        logger.info("Zona agregada: " + zona.getNombre());
    }

    public void eliminarZona(int id) {
        zonas.remove(id);
        logger.info("Zona eliminada ID: " + id);
    }

    public Map<Integer, Zona> obtenerTodasLasZonas() {
        return zonas;
    }
}
