package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Venta;
import com.panaderia.gestor.service.GestorVentas;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuVentas {
    public static void mostrarMenu(GestorVentas gestorVentas) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("REGISTRAR VENTA");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese el ID del producto: ");
            int productoId = scanner.nextInt();
            System.out.print("Ingrese la cantidad: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            gestorVentas.realizarVenta(productoId, cantidad);
            System.out.println("Venta registrada correctamente.");

            System.out.print("¿Desea registrar otra venta? (s/n): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("n")) {
                salir = true;
            }
        }
    }

    public static void mostrarVentas(GestorVentas gestorVentas) {
        System.out.println("--------------------------------------------------------");
        System.out.println("LISTA DE VENTAS");
        System.out.println("--------------------------------------------------------");

        List<Venta> ventas = gestorVentas.obtenerTodasLasVentas();
        String format = "| %-4s | %-20s | %-10s | %-10s | %-20s |%n";
        System.out.format("+------+----------------------+------------+------------+----------------------+%n");
        System.out.format("| ID   | Producto             | Cantidad   | Total      | Fecha                |%n");
        System.out.format("+------+----------------------+------------+------------+----------------------+%n");

        for (Venta venta : ventas) {
            System.out.format(format, venta.getId(), venta.getProducto().getNombre(), venta.getCantidad(), venta.getTotal(), venta.getFecha());
        }

        System.out.format("+------+----------------------+------------+------------+----------------------+%n");
    }
}
