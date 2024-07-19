package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.Turno;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GestorTurnos {
    private final Map<Integer, Empleado> empleados = new HashMap<>();
    private final Map<Integer, Turno> turnos = new HashMap<>();

    public Map<Integer, Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Map<Integer, Empleado> empleados) {
        this.empleados.clear();
        this.empleados.putAll(empleados);
    }

    public Map<Integer, Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Map<Integer, Turno> turnos) {
        this.turnos.clear();
        this.turnos.putAll(turnos);
    }

    public Empleado obtenerEmpleadoPorId(int id) {
        return empleados.get(id);
    }

    public Turno obtenerTurnoPorId(int id) {
        return turnos.get(id);
    }

    public void agregarTurno(int id, Empleado empleado, LocalTime horaInicio, LocalTime horaFin, Set<Integer> diasLaborables, String horario) {
        Turno turno = new Turno(id, empleado, horaInicio, horaFin, diasLaborables, horario);
        turnos.put(id, turno);
    }

    public void actualizarTurno(int id, LocalTime horaInicio, LocalTime horaFin, Set<Integer> diasLaborables, String horario) {
        Turno turno = turnos.get(id);
        if (turno != null) {
            turno.setHoraInicio(horaInicio);
            turno.setHoraFin(horaFin);
            turno.setDiasLaborables(diasLaborables);
            turno.setHorario(horario);
        }
    }

    public void eliminarTurno(int id) {
        turnos.remove(id);
    }

    public Map<Integer, Turno> obtenerTodosLosTurnos() {
        return new HashMap<>(turnos);
    }
}
