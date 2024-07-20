package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Producto;
import com.panaderia.gestor.model.Venta;
import com.panaderia.gestor.service.GestorVentas;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuVentas {
    public static void mostrarMenu(GestorVentas gestorVentas) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("MENÚ DE VENTAS");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Registrar Venta");
            System.out.println("2. Ver Ventas");
            System.out.println("3. Total de Ventas del Día");
            System.out.println("4. Total de Ventas de Otro Día");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-4]: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    registrarVenta(gestorVentas, scanner);
                    break;
                case 2:
                    mostrarVentas(gestorVentas);
                    break;
                case 3:
                    mostrarTotalVentasDelDia(gestorVentas);
                    break;
                case 4:
                    mostrarTotalVentasDeOtroDia(gestorVentas, scanner);
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }
    }

    private static void registrarVenta(GestorVentas gestorVentas, Scanner scanner) {
        System.out.print("Ingrese el ID del producto: ");
        int productoId = scanner.nextInt();
        Producto producto = gestorVentas.getProductoPorId(productoId);

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.println("Producto seleccionado: " + producto.getNombre());
        System.out.print("Ingrese la cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        gestorVentas.realizarVenta(productoId, cantidad);
        System.out.println("Venta registrada correctamente.");

        System.out.print("¿Desea registrar otra venta? (s/n): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("n")) {
            System.out.println("Volviendo al menú de ventas...");
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

    public static void mostrarTotalVentasDelDia(GestorVentas gestorVentas) {
        LocalDate hoy = LocalDate.now();
        List<Venta> ventasDelDia = gestorVentas.obtenerVentasPorFecha(hoy);
        double totalVentasDelDia = ventasDelDia.stream().mapToDouble(Venta::getTotal).sum();

        System.out.println("--------------------------------------------------------");
        System.out.println("TOTAL DE VENTAS DEL DÍA");
        System.out.println("--------------------------------------------------------");
        System.out.println("Fecha: " + hoy);
        System.out.println("Total de Ventas: S/" + totalVentasDelDia);

        mostrarReporteVentas(ventasDelDia);
    }

    public static void mostrarTotalVentasDeOtroDia(GestorVentas gestorVentas, Scanner scanner) {
        System.out.print("Ingrese la fecha para ver el total de ventas (yyyy-MM-dd): ");
        String fechaInput = scanner.nextLine();
        LocalDate fecha = LocalDate.parse(fechaInput);
        List<Venta> ventasDeOtroDia = gestorVentas.obtenerVentasPorFecha(fecha);
        double totalVentas = ventasDeOtroDia.stream().mapToDouble(Venta::getTotal).sum();

        System.out.println("--------------------------------------------------------");
        System.out.println("TOTAL DE VENTAS DE " + fecha);
        System.out.println("--------------------------------------------------------");
        System.out.println("Fecha: " + fecha);
        System.out.println("Total de Ventas: S/" + totalVentas);

        mostrarReporteVentas(ventasDeOtroDia);
    }

    private static void mostrarReporteVentas(List<Venta> ventas) {
        System.out.println("--------------------------------------------------------");
        System.out.println("REPORTE DE VENTAS");
        System.out.println("--------------------------------------------------------");
        String format = "| %-4s | %-20s | %-10s | %-10s | %-20s |%n";
        System.out.format("+------+----------------------+------------+------------+----------------------+%n");
        System.out.format("| ID   | Producto             | Cantidad   | Total      | Fecha                |%n");
        System.out.format("+------+----------------------+------------+------------+----------------------+%n");

        for (Venta venta : ventas) {
            System.out.format(format, venta.getId(), venta.getProducto().getNombre(), venta.getCantidad(), venta.getTotal(), venta.getFecha());
        }

        System.out.format("+------+----------------------+------------+------------+----------------------+%n");

        // Calcular subtotales y total
        double subtotal = ventas.stream().mapToDouble(Venta::getTotal).sum();
        System.out.println("Subtotal (IGV 18% Incl.): S/" + subtotal);
        // IGV es el 18% del subtotal
        // double igv = subtotal * 0.18;
        // System.out.println("IGV (18%): S/" + igv);
        //Por ahora no aplicamos IGV
        double total = subtotal;
        System.out.println("Total: S/" + total);
    }
}
