package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.Turno;
import com.panaderia.gestor.util.LoggerConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GestorTurnos {
    private Map<Integer, Empleado> empleados = new HashMap<>();
    private Map<Integer, Turno> turnos = new HashMap<>();
    private static final Logger logger = LoggerConfig.getLogger();

    public Map<Integer, Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Map<Integer, Empleado> empleados) {
        this.empleados = empleados;
    }

    public Map<Integer, Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Map<Integer, Turno> turnos) {
        this.turnos = turnos;
    }

    public Empleado getEmpleadoPorId(int id) {
        return empleados.get(id);
    }

    public Turno getTurnoPorId(int id) {
        return turnos.get(id);
    }

    public void agregarEmpleado(Empleado empleado) {
        empleados.put(empleado.getId(), empleado);
        logger.info("Empleado agregado: " + empleado.getNombre());
    }

    public void agregarTurno(Turno turno) {
        turnos.put(turno.getId(), turno);
        logger.info("Turno agregado para el empleado: " + turno.getEmpleado().getNombre());
    }

    public Map<Integer, Turno> obtenerTodosLosTurnos() {
        return turnos;
    }
}
