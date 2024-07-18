package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Empleado;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GestorAsistencia {
    private Map<Integer, Map<LocalDate, String>> asistencia;

    public GestorAsistencia() {
        try {
            Map<Integer, Empleado> empleados = DataLoader.cargarEmpleados();
            asistencia = DataLoader.cargarAsistencia(empleados);
        } catch (IOException e) {
            e.printStackTrace();
            asistencia = new HashMap<>();
        }
    }

    public void registrarAsistencia(int empleadoId, LocalDate fecha, String estado) {
        asistencia.putIfAbsent(empleadoId, new HashMap<>());
        asistencia.get(empleadoId).put(fecha, estado);
        try {
            DataLoader.guardarAsistencia(asistencia);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<LocalDate, String> obtenerAsistenciaPorEmpleado(int empleadoId) {
        return asistencia.get(empleadoId);
    }

    public Map<Integer, Map<LocalDate, String>> getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Map<Integer, Map<LocalDate, String>> asistencia) {
        this.asistencia = asistencia;
    }
}
