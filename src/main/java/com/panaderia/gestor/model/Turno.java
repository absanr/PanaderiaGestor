package com.panaderia.gestor.model;

import java.time.LocalTime;
import java.util.Set;

public class Turno {
    private int id;
    private Empleado empleado;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Set<Integer> diasLaborables;
    private String horario;

    public Turno(int id, Empleado empleado, LocalTime horaInicio, LocalTime horaFin, Set<Integer> diasLaborables, String horario) {
        this.id = id;
        this.empleado = empleado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.diasLaborables = diasLaborables;
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Set<Integer> getDiasLaborables() {
        return diasLaborables;
    }

    public void setDiasLaborables(Set<Integer> diasLaborables) {
        this.diasLaborables = diasLaborables;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
