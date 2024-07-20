package com.panaderia.gestor.model;

import java.time.LocalDate;

public class Empleado {
    private int id;
    private String nombre;
    private String rol;
    private double sueldo;
    private LocalDate fechaNacimiento;
    private LocalDate inicioContrato;
    private LocalDate finContrato;
    private LocalDate fechaPago;

    public Empleado(int id, String nombre, String rol, double sueldo, LocalDate fechaNacimiento, LocalDate inicioContrato, LocalDate finContrato, LocalDate fechaPago) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.sueldo = sueldo;
        this.fechaNacimiento = fechaNacimiento;
        this.inicioContrato = inicioContrato;
        this.finContrato = finContrato;
        this.fechaPago = fechaPago;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }
    public double getSueldo() { return sueldo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public LocalDate getInicioContrato() { return inicioContrato; }
    public LocalDate getFinContrato() { return finContrato; }
    public LocalDate getFechaPago() { return fechaPago; }
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRol(String rol) { this.rol = rol; }
    public void setSueldo(double sueldo) { this.sueldo = sueldo; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setInicioContrato(LocalDate inicioContrato) { this.inicioContrato = inicioContrato; }
    public void setFinContrato(LocalDate finContrato) { this.finContrato = finContrato; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
}
