package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.service.GestorAsistencia;
import com.panaderia.gestor.service.GestorTurnos;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuGestionAsistencia {
    public static void mostrarMenu(GestorAsistencia gestorAsistencia, GestorTurnos gestorTurnos) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("GESTIÓN DE ASISTENCIA");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Registrar Asistencia");
            System.out.println("2. Ver Asistencia");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-2]: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        registrarAsistencia(gestorAsistencia, gestorTurnos, scanner);
                        break;
                    case 2:
                        verAsistencia(gestorAsistencia);
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

    private static void registrarAsistencia(GestorAsistencia gestorAsistencia, GestorTurnos gestorTurnos, Scanner scanner) {
        System.out.println("Lista de Empleados:");
        mostrarListaEmpleados(gestorTurnos);

        System.out.print("Seleccione el ID del empleado: ");
        int empleadoId = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Empleado empleado = gestorTurnos.getEmpleadoPorId(empleadoId);
        if (empleado != null) {
            System.out.print("Ingrese la fecha de la asistencia (yyyy-MM-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());
            System.out.print("Ingrese el estado de la asistencia (true/false/tarde): ");
            String estado = scanner.nextLine();

            gestorAsistencia.registrarAsistencia(empleadoId, fecha, estado);
            System.out.println("Asistencia registrada correctamente.");
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private static void verAsistencia(GestorAsistencia gestorAsistencia) {
        System.out.println("--------------------------------------------------------");
        System.out.println("LISTA DE ASISTENCIA");
        System.out.println("--------------------------------------------------------");

        Map<Integer, Map<LocalDate, String>> asistencia = gestorAsistencia.getAsistencia();
        String format = "| %-10s | %-10s | %-6s |%n";
        System.out.format("+------------+------------+--------+%n");
        System.out.format("| EmpleadoID | Fecha      | Estado |%n");
        System.out.format("+------------+------------+--------+%n");

        for (Map.Entry<Integer, Map<LocalDate, String>> entry : asistencia.entrySet()) {
            int empleadoId = entry.getKey();
            Map<LocalDate, String> asistencias = entry.getValue();
            for (Map.Entry<LocalDate, String> asistenciaEntry : asistencias.entrySet()) {
                System.out.format(format, empleadoId, asistenciaEntry.getKey(), asistenciaEntry.getValue());
            }
        }

        System.out.format("+------------+------------+--------+%n");
    }

    private static void mostrarListaEmpleados(GestorTurnos gestorTurnos) {
        Map<Integer, Empleado> empleados = gestorTurnos.getEmpleados();
        String format = "| %-4s | %-20s | %-15s |%n";
        System.out.format("+------+----------------------+-----------------+%n");
        System.out.format("| ID   | Nombre               | Rol             |%n");
        System.out.format("+------+----------------------+-----------------+%n");

        for (Empleado empleado : empleados.values()) {
            System.out.format(format, empleado.getId(), empleado.getNombre(), empleado.getRol());
        }

        System.out.format("+------+----------------------+-----------------+%n");
    }
}
