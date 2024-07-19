package com.panaderia.gestor.model;

import java.time.LocalDateTime;

public class Turno {
    private int id;
    private Empleado empleado;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private String horario; // "mañana" o "tarde"

    public Turno(int id, Empleado empleado, LocalDateTime inicio, LocalDateTime fin, String horario) {
        this.id = id;
        this.empleado = empleado;
        this.inicio = inicio;
        this.fin = fin;
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", empleado=" + empleado.getNombre() +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", horario='" + horario + '\'' +
                '}';
    }
}
