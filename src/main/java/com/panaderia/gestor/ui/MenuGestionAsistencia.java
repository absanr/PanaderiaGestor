package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Usuario;
import com.panaderia.gestor.service.GestorAsistencia;
import com.panaderia.gestor.service.GestorTurnos;

import java.time.LocalDate;
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
            System.out.println("3. Actualizar Asistencia");
            System.out.println("4. Eliminar Asistencia");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-4]: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea
            switch (opcion) {
                case 1:
                    registrarAsistencia(gestorAsistencia, scanner);
                    break;
                case 2:
                    verAsistencia(gestorAsistencia, gestorTurnos);
                    break;
                case 3:
                    actualizarAsistencia(gestorAsistencia, scanner);
                    break;
                case 4:
                    eliminarAsistencia(gestorAsistencia, scanner);
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }
    }

    public static void mostrarMenuEmpleado(GestorAsistencia gestorAsistencia, Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("VER ASISTENCIA PROPIA");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Ver Asistencia");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-1]: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea
            switch (opcion) {
                case 1:
                    verAsistenciaEmpleado(gestorAsistencia, usuario);
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }
    }

    private static void registrarAsistencia(GestorAsistencia gestorAsistencia, Scanner scanner) {
        System.out.print("Ingrese el ID del empleado: ");
        int empleadoId = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        System.out.print("Ingrese la fecha de la asistencia (yyyy-MM-dd): ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese el estado (PRESENTE/AUSENTE/TARDE): ");
        String estado = scanner.nextLine();

        gestorAsistencia.registrarAsistencia(empleadoId, LocalDate.parse(fecha), estado);
        System.out.println("Asistencia registrada correctamente.");
    }

    private static void verAsistencia(GestorAsistencia gestorAsistencia, GestorTurnos gestorTurnos) {
        System.out.println("--------------------------------------------------------");
        System.out.println("LISTA DE ASISTENCIA");
        System.out.println("--------------------------------------------------------");

        Map<Integer, Map<LocalDate, String>> asistencia = gestorAsistencia.getAsistencia();
        String format = "| %-4s | %-20s | %-10s | %-10s |%n";
        System.out.format("+------+----------------------+------------+------------+%n");
        System.out.format("| ID   | Empleado             | Fecha      | Estado     |%n");
        System.out.format("+------+----------------------+------------+------------+%n");

        for (Map.Entry<Integer, Map<LocalDate, String>> entry : asistencia.entrySet()) {
            int empleadoId = entry.getKey();
            String empleadoNombre = gestorTurnos.getEmpleadoPorId(empleadoId).getNombre();
            for (Map.Entry<LocalDate, String> asistenciaEntry : entry.getValue().entrySet()) {
                System.out.format(format, empleadoId, empleadoNombre, asistenciaEntry.getKey(), asistenciaEntry.getValue());
            }
        }

        System.out.format("+------+----------------------+------------+------------+%n");
    }

    private static void verAsistenciaEmpleado(GestorAsistencia gestorAsistencia, Usuario usuario) {
        System.out.println("--------------------------------------------------------");
        System.out.println("ASISTENCIA DEL EMPLEADO");
        System.out.println("--------------------------------------------------------");

        int empleadoId = usuario.getUsername().hashCode();  // Using username hashcode as ID
        Map<LocalDate, String> asistencia = gestorAsistencia.getAsistenciaPorEmpleado(empleadoId);
        String format = "| %-10s | %-10s |%n";
        System.out.format("+------------+------------+%n");
        System.out.format("| Fecha      | Estado     |%n");
        System.out.format("+------------+------------+%n");

        for (Map.Entry<LocalDate, String> entry : asistencia.entrySet()) {
            System.out.format(format, entry.getKey(), entry.getValue());
        }

        System.out.format("+------------+------------+%n");
    }

    private static void actualizarAsistencia(GestorAsistencia gestorAsistencia, Scanner scanner) {
        System.out.print("Ingrese el ID del empleado: ");
        int empleadoId = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        System.out.print("Ingrese la fecha de la asistencia (yyyy-MM-dd): ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese el nuevo estado (PRESENTE/AUSENTE/TARDE): ");
        String estado = scanner.nextLine();

        gestorAsistencia.actualizarAsistencia(empleadoId, LocalDate.parse(fecha), estado);
        System.out.println("Asistencia actualizada correctamente.");
    }

    private static void eliminarAsistencia(GestorAsistencia gestorAsistencia, Scanner scanner) {
        System.out.print("Ingrese el ID del empleado: ");
        int empleadoId = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        System.out.print("Ingrese la fecha de la asistencia (yyyy-MM-dd): ");
        String fecha = scanner.nextLine();

        gestorAsistencia.eliminarAsistencia(empleadoId, LocalDate.parse(fecha));
        System.out.println("Asistencia eliminada correctamente.");
    }
}
