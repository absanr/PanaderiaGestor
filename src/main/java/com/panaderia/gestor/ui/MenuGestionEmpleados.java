package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.service.DataLoader;
import com.panaderia.gestor.service.GestorTurnos;
import com.panaderia.gestor.service.IdGenerator;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuGestionEmpleados {
    public static void mostrarMenu(GestorTurnos gestorTurnos) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("GESTIÓN DE EMPLEADOS");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Crear Empleado");
            System.out.println("2. Ver Empleados");
            System.out.println("3. Actualizar Empleado");
            System.out.println("4. Eliminar Empleado");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-4]: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        crearEmpleado(gestorTurnos, scanner);
                        break;
                    case 2:
                        verEmpleados(gestorTurnos);
                        break;
                    case 3:
                        actualizarEmpleado(gestorTurnos, scanner);
                        break;
                    case 4:
                        eliminarEmpleado(gestorTurnos, scanner);
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

    private static void crearEmpleado(GestorTurnos gestorTurnos, Scanner scanner) {
        try {
            int id = IdGenerator.obtenerSiguienteId("empleados.txt");
            System.out.print("Ingrese el nombre del empleado: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el rol del empleado: ");
            String rol = scanner.nextLine();
            System.out.print("Ingrese el sueldo del empleado: ");
            double sueldo = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea

            Empleado empleado = new Empleado(id, nombre, rol, sueldo);
            gestorTurnos.getEmpleados().put(id, empleado);
            DataLoader.guardarEmpleados(gestorTurnos.getEmpleados());
            System.out.println("Empleado creado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al crear el empleado: " + e.getMessage());
        }
    }

    private static void verEmpleados(GestorTurnos gestorTurnos) {
        System.out.println("--------------------------------------------------------");
        System.out.println("LISTA DE EMPLEADOS");
        System.out.println("--------------------------------------------------------");

        Map<Integer, Empleado> empleados = gestorTurnos.getEmpleados();
        String format = "| %-4s | %-20s | %-15s | %-8s |%n";
        System.out.format("+------+----------------------+-----------------+----------+%n");
        System.out.format("| ID   | Nombre               | Rol             | Sueldo   |%n");
        System.out.format("+------+----------------------+-----------------+----------+%n");

        for (Empleado empleado : empleados.values()) {
            System.out.format(format, empleado.getId(), empleado.getNombre(), empleado.getRol(), empleado.getSueldo());
        }

        System.out.format("+------+----------------------+-----------------+----------+%n");
    }

    private static void actualizarEmpleado(GestorTurnos gestorTurnos, Scanner scanner) {
        System.out.print("Ingrese el ID del empleado a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Empleado empleado = gestorTurnos.getEmpleadoPorId(id);
        if (empleado != null) {
            System.out.print("Ingrese el nuevo nombre del empleado: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el nuevo rol del empleado: ");
            String rol = scanner.nextLine();
            System.out.print("Ingrese el nuevo sueldo del empleado: ");
            double sueldo = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea

            empleado.setNombre(nombre);
            empleado.setRol(rol);
            empleado.setSueldo(sueldo);
            try {
                DataLoader.guardarEmpleados(gestorTurnos.getEmpleados());
                System.out.println("Empleado actualizado correctamente.");
            } catch (IOException e) {
                System.out.println("Error al actualizar el empleado: " + e.getMessage());
            }
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private static void eliminarEmpleado(GestorTurnos gestorTurnos, Scanner scanner) {
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Empleado empleado = gestorTurnos.getEmpleadoPorId(id);
        if (empleado != null) {
            gestorTurnos.getEmpleados().remove(id);
            try {
                DataLoader.guardarEmpleados(gestorTurnos.getEmpleados());
                System.out.println("Empleado eliminado correctamente.");
            } catch (IOException e) {
                System.out.println("Error al eliminar el empleado: " + e.getMessage());
            }
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }
}
