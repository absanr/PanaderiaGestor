package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Producto;
import com.panaderia.gestor.service.DataLoader;
import com.panaderia.gestor.service.GestorVentas;
import com.panaderia.gestor.service.IdGenerator;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuProductos {
    public static void mostrarMenu(GestorVentas gestorVentas) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("GESTIÓN DE PRODUCTOS");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Ver Productos");
            System.out.println("3. Actualizar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-4]: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        agregarProducto(gestorVentas, scanner);
                        break;
                    case 2:
                        verProductos(gestorVentas);
                        break;
                    case 3:
                        actualizarProducto(gestorVentas, scanner);
                        break;
                    case 4:
                        eliminarProducto(gestorVentas, scanner);
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpiar la entrada inválida
            }
        }
    }

    private static void agregarProducto(GestorVentas gestorVentas, Scanner scanner) {
        try {
            int id = IdGenerator.obtenerSiguienteId("productos.txt");
            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el precio de venta del producto: ");
            double precioVenta = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea
            System.out.print("Ingrese el costo de producción del producto: ");
            double costoProduccion = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea
            System.out.print("Ingrese el stock del producto: ");
            int stock = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            Producto producto = new Producto(id, nombre, precioVenta, costoProduccion, stock);
            gestorVentas.getProductos().add(producto);
            DataLoader.guardarProductos(gestorVentas.getProductos());
            System.out.println("Producto agregado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al agregar el producto: " + e.getMessage());
        }
    }

    private static void verProductos(GestorVentas gestorVentas) {
        System.out.println("--------------------------------------------------------");
        System.out.println("LISTA DE PRODUCTOS");
        System.out.println("--------------------------------------------------------");

        List<Producto> productos = gestorVentas.getProductos();
        String format = "| %-4s | %-20s | %-10s | %-10s | %-10s |%n";
        System.out.format("+------+----------------------+------------+------------+------------+%n");
        System.out.format("| ID   | Nombre               | Precio     | Costo      | Stock      |%n");
        System.out.format("+------+----------------------+------------+------------+------------+%n");

        for (Producto producto : productos) {
            System.out.format(format, producto.getId(), producto.getNombre(), producto.getPrecioVenta(), producto.getCostoProduccion(), producto.getStock());
        }

        System.out.format("+------+----------------------+------------+------------+------------+%n");
    }

    private static void actualizarProducto(GestorVentas gestorVentas, Scanner scanner) {
        System.out.print("Ingrese el ID del producto a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Producto producto = gestorVentas.getProductoPorId(id);
        if (producto != null) {
            System.out.print("Ingrese el nuevo nombre del producto: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el nuevo precio de venta del producto: ");
            double precioVenta = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea
            System.out.print("Ingrese el nuevo costo de producción del producto: ");
            double costoProduccion = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea
            System.out.print("Ingrese el nuevo stock del producto: ");
            int stock = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            producto.setNombre(nombre);
            producto.setPrecioVenta(precioVenta);
            producto.setCostoProduccion(costoProduccion);
            producto.setStock(stock);
            try {
                DataLoader.guardarProductos(gestorVentas.getProductos());
                System.out.println("Producto actualizado correctamente.");
            } catch (IOException e) {
                System.out.println("Error al actualizar el producto: " + e.getMessage());
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void eliminarProducto(GestorVentas gestorVentas, Scanner scanner) {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Producto producto = gestorVentas.getProductoPorId(id);
        if (producto != null) {
            gestorVentas.getProductos().removeIf(p -> p.getId() == id);
            try {
                DataLoader.guardarProductos(gestorVentas.getProductos());
                System.out.println("Producto eliminado correctamente.");
            } catch (IOException e) {
                System.out.println("Error al eliminar el producto: " + e.getMessage());
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }
}
