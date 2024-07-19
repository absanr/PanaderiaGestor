package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Turno;
import com.panaderia.gestor.service.DataLoader;
import com.panaderia.gestor.service.GestorTurnos;
import com.panaderia.gestor.service.IdGenerator;

import java.io.IOException;
import java.time.LocalDateTime;
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
            System.out.println("1. Crear Turno");
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
                        crearTurno(gestorTurnos, scanner);
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

    private static void crearTurno(GestorTurnos gestorTurnos, Scanner scanner) {
        try {
            int id = IdGenerator.obtenerSiguienteId("turnos.txt");
            System.out.print("Ingrese el ID del empleado: ");
            int empleadoId = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea
            System.out.print("Ingrese la fecha y hora de inicio del turno (yyyy-MM-ddTHH:mm): ");
            String inicio = scanner.nextLine();
            System.out.print("Ingrese la fecha y hora de fin del turno (yyyy-MM-ddTHH:mm): ");
            String fin = scanner.nextLine();
            System.out.print("Ingrese el horario (mañana/tarde): ");
            String horario = scanner.nextLine();

            Turno turno = new Turno(id, gestorTurnos.getEmpleadoPorId(empleadoId), LocalDateTime.parse(inicio), LocalDateTime.parse(fin), horario);
            gestorTurnos.getTurnos().put(id, turno);
            DataLoader.guardarTurnos(gestorTurnos.getTurnos());
            System.out.println("Turno creado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al crear el turno: " + e.getMessage());
        }
    }

    private static void verTurnos(GestorTurnos gestorTurnos) {
        System.out.println("--------------------------------------------------------");
        System.out.println("LISTA DE TURNOS");
        System.out.println("--------------------------------------------------------");

        Map<Integer, Turno> turnos = gestorTurnos.getTurnos();
        String format = "| %-4s | %-20s | %-20s | %-20s | %-10s |%n";
        System.out.format("+------+----------------------+----------------------+----------------------+------------+%n");
        System.out.format("| ID   | Empleado             | Inicio               | Fin                  | Horario    |%n");
        System.out.format("+------+----------------------+----------------------+----------------------+------------+%n");

        for (Turno turno : turnos.values()) {
            System.out.format(format, turno.getId(), turno.getEmpleado().getNombre(), turno.getInicio(), turno.getFin(), turno.getHorario());
        }

        System.out.format("+------+----------------------+----------------------+----------------------+------------+%n");
    }

    private static void actualizarTurno(GestorTurnos gestorTurnos, Scanner scanner) {
        System.out.print("Ingrese el ID del turno a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Turno turno = gestorTurnos.getTurnoPorId(id);
        if (turno != null) {
            System.out.print("Ingrese la nueva fecha y hora de inicio del turno (yyyy-MM-ddTHH:mm): ");
            String inicio = scanner.nextLine();
            System.out.print("Ingrese la nueva fecha y hora de fin del turno (yyyy-MM-ddTHH:mm): ");
            String fin = scanner.nextLine();
            System.out.print("Ingrese el nuevo horario (mañana/tarde): ");
            String horario = scanner.nextLine();

            turno.setInicio(LocalDateTime.parse(inicio));
            turno.setFin(LocalDateTime.parse(fin));
            turno.setHorario(horario);
            try {
                DataLoader.guardarTurnos(gestorTurnos.getTurnos());
                System.out.println("Turno actualizado correctamente.");
            } catch (IOException e) {
                System.out.println("Error al actualizar el turno: " + e.getMessage());
            }
        } else {
            System.out.println("Turno no encontrado.");
        }
    }

    private static void eliminarTurno(GestorTurnos gestorTurnos, Scanner scanner) {
        System.out.print("Ingrese el ID del turno a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Turno turno = gestorTurnos.getTurnoPorId(id);
        if (turno != null) {
            gestorTurnos.getTurnos().remove(id);
            try {
                DataLoader.guardarTurnos(gestorTurnos.getTurnos());
                System.out.println("Turno eliminado correctamente.");
            } catch (IOException e) {
                System.out.println("Error al eliminar el turno: " + e.getMessage());
            }
        } else {
            System.out.println("Turno no encontrado.");
        }
    }
}
