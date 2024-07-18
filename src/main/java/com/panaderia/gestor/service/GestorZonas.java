package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Zona;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GestorZonas {
    private Map<Integer, Zona> zonas;

    public GestorZonas() {
        zonas = new HashMap<>();
    }

    public void agregarZona(Zona zona) {
        zonas.put(zona.getId(), zona);
    }

    public void eliminarZona(int id) {
        zonas.remove(id);
    }

    public Zona obtenerZonaPorId(int id) {
        return zonas.get(id);
    }

    public Map<Integer, Zona> obtenerTodasLasZonas() {
        return zonas;
    }

    public void cargarZonas() throws IOException {
        zonas = DataLoader.cargarZonas();
    }

    public void guardarZonas() throws IOException {
        DataLoader.guardarZonas(zonas);
    }

    public int generarSiguienteId() throws IOException {
        return IdGenerator.obtenerSiguienteId("zonas.txt");
    }

    public void setZonas(Map<Integer, Zona> zonas) {
        this.zonas = zonas;
    }
}
