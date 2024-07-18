package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.Turno;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GestorTurnos {
    private Map<Integer, Turno> turnos;
    private Map<Integer, Empleado> empleados;

    public GestorTurnos() {
        turnos = new HashMap<>();
        empleados = new HashMap<>();
    }

    public void agregarTurno(Turno turno) throws IOException {
        turnos.put(turno.getId(), turno);
        guardarTurnos();
    }

    public void actualizarTurno(Turno turno) throws IOException {
        turnos.put(turno.getId(), turno);
        guardarTurnos();
    }

    public void eliminarTurno(int id) throws IOException {
        turnos.remove(id);
        guardarTurnos();
    }

    public Turno obtenerTurnoPorId(int id) {
        return turnos.get(id);
    }

    public Map<Integer, Turno> obtenerTodosLosTurnos() {
        return turnos;
    }

    public Empleado getEmpleadoPorId(int id) {
        return empleados.get(id);
    }

    public void cargarTurnos() throws IOException {
        turnos = DataLoader.cargarTurnos(empleados);
    }

    public void guardarTurnos() throws IOException {
        DataLoader.guardarTurnos(turnos);
    }

    public int generarSiguienteId() throws IOException {
        return IdGenerator.obtenerSiguienteId("turnos.txt");
    }

    public Map<Integer, Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Map<Integer, Empleado> empleados) {
        this.empleados = empleados;
    }

    public void setTurnos(Map<Integer, Turno> turnos) {
        this.turnos = turnos;
    }
}
