package com.panaderia.gestor.model;

public class Producto {
    private int id;
    private String nombre;
    private double precioVenta;
    private double costoProduccion;
    private int stock;

    public Producto(int id, String nombre, double precioVenta, double costoProduccion, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.costoProduccion = costoProduccion;
        this.stock = stock;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public double getCostoProduccion() {
        return costoProduccion;
    }

    public int getStock() {
        return stock;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setCostoProduccion(double costoProduccion) {
        this.costoProduccion = costoProduccion;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
