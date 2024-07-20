package com.panaderia.gestor.model;

import java.time.LocalDateTime;

public class Venta {
    private int id;
    private Producto producto;
    private int cantidad;
    private double total;
    private LocalDateTime fecha;

    public Venta(int id, Producto producto, int cantidad, double total, LocalDateTime fecha) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha = fecha;
    }

    // Getters y setters
    public int getId() { return id; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getTotal() { return total; }
    public LocalDateTime getFecha() { return fecha; }
    public void setId(int id) { this.id = id; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setTotal(double total) { this.total = total; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
