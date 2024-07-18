package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Zona;
import com.panaderia.gestor.service.DataLoader;
import com.panaderia.gestor.service.GestorZonas;
import com.panaderia.gestor.service.IdGenerator;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuGestionZonas {
    public static void mostrarMenu(GestorZonas gestorZonas) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("GESTIÓN DE ZONAS");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Crear Zona");
            System.out.println("2. Ver Zonas");
            System.out.println("3. Actualizar Zona");
            System.out.println("4. Eliminar Zona");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-4]: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        crearZona(gestorZonas, scanner);
                        break;
                    case 2:
                        verZonas(gestorZonas);
                        break;
                    case 3:
                        actualizarZona(gestorZonas, scanner);
                        break;
                    case 4:
                        eliminarZona(gestorZonas, scanner);
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

    private static void crearZona(GestorZonas gestorZonas, Scanner scanner) {
        try {
            int id = IdGenerator.obtenerSiguienteId("zonas.txt");
            System.out.print("Ingrese el nombre de la zona: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese la descripción de la zona: ");
            String descripcion = scanner.nextLine();
            Zona zona = new Zona(id, nombre, descripcion);
            gestorZonas.agregarZona(zona);
            DataLoader.guardarZonas(gestorZonas.obtenerTodasLasZonas());
            System.out.println("Zona creada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al crear la zona: " + e.getMessage());
        }
    }

    private static void verZonas(GestorZonas gestorZonas) {
        System.out.println("--------------------------------------------------------");
        System.out.println("LISTA DE ZONAS");
        System.out.println("--------------------------------------------------------");

        Map<Integer, Zona> zonas = gestorZonas.obtenerTodasLasZonas();
        String format = "| %-4s | %-20s | %-30s |%n";
        System.out.format("+------+----------------------+--------------------------------+%n");
        System.out.format("| ID   | Nombre               | Descripción                    |%n");
        System.out.format("+------+----------------------+--------------------------------+%n");

        for (Zona zona : zonas.values()) {
            System.out.format(format, zona.getId(), zona.getNombre(), zona.getDescripcion());
        }

        System.out.format("+------+----------------------+--------------------------------+%n");
    }

    private static void actualizarZona(GestorZonas gestorZonas, Scanner scanner) {
        verZonas(gestorZonas);
        System.out.print("Seleccione el ID de la zona a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Zona zona = gestorZonas.obtenerZonaPorId(id);
        if (zona != null) {
            System.out.print("Ingrese el nuevo nombre de la zona: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese la nueva descripción de la zona: ");
            String descripcion = scanner.nextLine();
            zona.setNombre(nombre);
            zona.setDescripcion(descripcion);
            try {
                DataLoader.guardarZonas(gestorZonas.obtenerTodasLasZonas());
                System.out.println("Zona actualizada correctamente.");
            } catch (IOException e) {
                System.out.println("Error al actualizar la zona: " + e.getMessage());
            }
        } else {
            System.out.println("Zona no encontrada.");
        }
    }

    private static void eliminarZona(GestorZonas gestorZonas, Scanner scanner) {
        verZonas(gestorZonas);
        System.out.print("Seleccione el ID de la zona a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Zona zona = gestorZonas.obtenerZonaPorId(id);
        if (zona != null) {
            gestorZonas.eliminarZona(id);
            try {
                DataLoader.guardarZonas(gestorZonas.obtenerTodasLasZonas());
                System.out.println("Zona eliminada correctamente.");
            } catch (IOException e) {
                System.out.println("Error al eliminar la zona: " + e.getMessage());
            }
        } else {
            System.out.println("Zona no encontrada.");
        }
    }
}
