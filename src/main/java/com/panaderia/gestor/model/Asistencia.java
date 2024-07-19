package com.panaderia.gestor.model;

import java.time.LocalDate;

public class Asistencia {
    private int empleadoId;
    private LocalDate fecha;
    private String estado; // Puede ser "PRESENTE", "AUSENTE", "TARDE"

    public Asistencia(int empleadoId, LocalDate fecha, String estado) {
        this.empleadoId = empleadoId;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Asistencia{" +
                "empleadoId=" + empleadoId +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                '}';
    }
}
