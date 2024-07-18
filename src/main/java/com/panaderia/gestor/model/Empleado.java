package com.panaderia.gestor.model;

public class Empleado {
    private int id;
    private String nombre;
    private String rol;
    private double sueldo;

    public Empleado(int id, String nombre, String rol, double sueldo) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.sueldo = sueldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                ", sueldo=" + sueldo +
                '}';
    }
}
