package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.util.LoggerConfig;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GestorAsistencia {
    private Map<Integer, Map<LocalDate, String>> asistencia = new HashMap<>();
    private static final Logger logger = LoggerConfig.getLogger();

    public Map<Integer, Map<LocalDate, String>> getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Map<Integer, Map<LocalDate, String>> asistencia) {
        this.asistencia = asistencia;
    }

    public Map<LocalDate, String> getAsistenciaPorEmpleado(int empleadoId) {
        return asistencia.get(empleadoId);
    }

    public void registrarAsistencia(int empleadoId, LocalDate fecha, String estado) {
        asistencia.putIfAbsent(empleadoId, new HashMap<>());
        asistencia.get(empleadoId).put(fecha, estado);
        logger.info("Asistencia registrada para el empleado ID: " + empleadoId + " en fecha: " + fecha);
    }

    public void actualizarAsistencia(int empleadoId, LocalDate fecha, String estado) {
        if (asistencia.containsKey(empleadoId)) {
            asistencia.get(empleadoId).put(fecha, estado);
            logger.info("Asistencia actualizada para el empleado ID: " + empleadoId + " en fecha: " + fecha);
        }
    }

    public void eliminarAsistencia(int empleadoId, LocalDate fecha) {
        if (asistencia.containsKey(empleadoId)) {
            asistencia.get(empleadoId).remove(fecha);
            logger.info("Asistencia eliminada para el empleado ID: " + empleadoId + " en fecha: " + fecha);
        }
    }
}
