package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.Turno;
import com.panaderia.gestor.service.GestorTurnos;
import com.panaderia.gestor.service.IdGenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuGestionTurnos {
    public static void mostrarMenu(GestorTurnos gestorTurnos) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("GESTIÓN DE TURNOS");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Asignar Turno");
            System.out.println("2. Ver Turnos");
            System.out.println("3. Actualizar Turno");
            System.out.println("4. Eliminar Turno");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-4]: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        asignarTurno(gestorTurnos, scanner);
                        break;
                    case 2:
                        verTurnos(gestorTurnos);
                        break;
                    case 3:
                        actualizarTurno(gestorTurnos, scanner);
                        break;
                    case 4:
                        eliminarTurno(gestorTurnos, scanner);
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

    private static void asignarTurno(GestorTurnos gestorTurnos, Scanner scanner) {
        try {
            int id = IdGenerator.obtenerSiguienteId("turnos.txt");
            System.out.println("Lista de Empleados:");
            mostrarListaEmpleados(gestorTurnos);

            System.out.print("Seleccione el ID del empleado: ");
            int empleadoId = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            Empleado empleado = gestorTurnos.getEmpleadoPorId(empleadoId);
            if (empleado != null) {
                LocalDateTime inicio = ingresarFechaYHora(scanner, "inicio");
                LocalDateTime fin = ingresarFechaYHora(scanner, "fin");

                Turno turno = new Turno(id, empleado, inicio, fin);
                gestorTurnos.agregarTurno(turno);
                System.out.println("Turno asignado correctamente.");
            } else {
                System.out.println("Empleado no encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Error al asignar el turno: " + e.getMessage());
        }
    }

    private static LocalDateTime ingresarFechaYHora(Scanner scanner, String tipo) {
        System.out.println("Ingrese la fecha y hora de " + tipo + " del turno:");

        System.out.print("Año (YYYY): ");
        int anio = scanner.nextInt();
        System.out.print("Mes (MM): ");
        int mes = scanner.nextInt();
        System.out.print("Día (DD): ");
        int dia = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        System.out.print("Hora (HH): ");
        int hora = scanner.nextInt();
        System.out.print("Minuto (MM): ");
        int minuto = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        return LocalDateTime.of(LocalDate.of(anio, mes, dia), LocalTime.of(hora, minuto));
    }

    private static void verTurnos(GestorTurnos gestorTurnos) {
        System.out.println("--------------------------------------------------------");
        System.out.println("LISTA DE TURNOS");
        System.out.println("--------------------------------------------------------");

        Map<Integer, Turno> turnos = gestorTurnos.obtenerTodosLosTurnos();
        String format = "| %-4s | %-20s | %-20s | %-20s |%n";
        System.out.format("+------+----------------------+----------------------+----------------------+%n");
        System.out.format("| ID   | Empleado             | Inicio               | Fin                  |%n");
        System.out.format("+------+----------------------+----------------------+----------------------+%n");

        for (Turno turno : turnos.values()) {
            System.out.format(format, turno.getId(), turno.getEmpleado().getNombre(), turno.getInicio(), turno.getFin());
        }

        System.out.format("+------+----------------------+----------------------+----------------------+%n");
    }

    private static void actualizarTurno(GestorTurnos gestorTurnos, Scanner scanner) {
        try {
            verTurnos(gestorTurnos);
            System.out.print("Seleccione el ID del turno a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            Turno turno = gestorTurnos.obtenerTurnoPorId(id);
            if (turno != null) {
                LocalDateTime inicio = ingresarFechaYHora(scanner, "nuevo inicio");
                LocalDateTime fin = ingresarFechaYHora(scanner, "nuevo fin");

                turno.setInicio(inicio);
                turno.setFin(fin);
                gestorTurnos.actualizarTurno(turno);
                System.out.println("Turno actualizado correctamente.");
            } else {
                System.out.println("Turno no encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el turno: " + e.getMessage());
        }
    }

    private static void eliminarTurno(GestorTurnos gestorTurnos, Scanner scanner) {
        try {
            verTurnos(gestorTurnos);
            System.out.print("Seleccione el ID del turno a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            Turno turno = gestorTurnos.obtenerTurnoPorId(id);
            if (turno != null) {
                gestorTurnos.eliminarTurno(id);
                System.out.println("Turno eliminado correctamente.");
            } else {
                System.out.println("Turno no encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar el turno: " + e.getMessage());
        }
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
