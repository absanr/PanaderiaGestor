package com.panaderia.gestor.model;

import java.util.ArrayList;
import java.util.List;

public class Negocio {
    private double capitalInicial;
    private double gastos;
    private double ganancias;
    private List<Producto> productos;
    private List<Venta> ventas;

    public Negocio(double capitalInicial) {
        this.capitalInicial = capitalInicial;
        this.gastos = 0;
        this.ganancias = 0;
        this.productos = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public double getCapitalInicial() {
        return capitalInicial;
    }

    public double getGastos() {
        return gastos;
    }

    public double getGanancias() {
        return ganancias;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    public void registrarVenta(Venta venta) {
        this.ventas.add(venta);
        this.ganancias += venta.getTotal();
        for (Producto producto : productos) {
            if (producto.getId() == venta.getProducto().getId()) {
                producto.setStock(producto.getStock() - venta.getCantidad());
            }
        }
    }

    public void agregarGasto(double monto) {
        this.gastos += monto;
    }

    public double calcularGananciasAnuales() {
        return capitalInicial + ganancias - gastos;
    }
}
