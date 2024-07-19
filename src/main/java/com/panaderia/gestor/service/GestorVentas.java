package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Producto;
import com.panaderia.gestor.model.Venta;
import com.panaderia.gestor.util.LoggerConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GestorVentas {
    private List<Producto> productos = new ArrayList<>();
    private List<Venta> ventas = new ArrayList<>();
    private static final Logger logger = LoggerConfig.getLogger();

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        logger.info("Producto agregado: " + producto.getNombre());
    }

    public void eliminarProducto(int id) {
        productos.removeIf(producto -> producto.getId() == id);
        logger.info("Producto eliminado ID: " + id);
    }

    public Producto getProductoPorId(int id) {
        for (Producto producto : productos) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }

    public void realizarVenta(int productoId, int cantidad) {
        Producto producto = getProductoPorId(productoId);
        if (producto != null && producto.getStock() >= cantidad) {
            double total = producto.getPrecioVenta() * cantidad;
            Venta venta = new Venta(ventas.size() + 1, producto, cantidad, total, LocalDateTime.now());
            ventas.add(venta);
            producto.setStock(producto.getStock() - cantidad);
            logger.info("Venta realizada: " + cantidad + " " + producto.getNombre() + " por un total de " + total);
        } else {
            logger.warning("No hay suficiente stock o el producto no existe.");
            System.out.println("No hay suficiente stock o el producto no existe.");
        }
    }

    public List<Venta> obtenerTodasLasVentas() {
        return ventas;
    }
}
