package com.panaderia.gestor.model;

import java.time.LocalDate;

public class EmpleadoPago {
    private int empleadoId;
    private LocalDate fechaPago;
    private double monto;
    private String estado;

    public EmpleadoPago(int empleadoId, LocalDate fechaPago, double monto, String estado) {
        this.empleadoId = empleadoId;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.estado = estado;
    }

    // Getters y setters

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
