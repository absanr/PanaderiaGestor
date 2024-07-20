package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Turno;
import com.panaderia.gestor.service.DataLoader;
import com.panaderia.gestor.service.GestorTurnos;
import com.panaderia.gestor.service.IdGenerator;

import java.io.IOException;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            System.out.print("Ingrese la hora de inicio del turno (HH:mm): ");
            String inicio = scanner.nextLine();
            System.out.print("Ingrese la hora de fin del turno (HH:mm): ");
            String fin = scanner.nextLine();
            System.out.print("Ingrese los días laborables (1=Lunes, 7=Domingo, separados por ';', ej. 1;2;3): ");
            String dias = scanner.nextLine();
            System.out.print("Ingrese el horario (mañana/tarde): ");
            String horario = scanner.nextLine();

            Set<Integer> diasLaborables = Stream.of(dias.split(";"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());

            gestorTurnos.agregarTurno(id, gestorTurnos.obtenerEmpleadoPorId(empleadoId), LocalTime.parse(inicio), LocalTime.parse(fin), diasLaborables, horario);
            DataLoader.guardarTurnos(gestorTurnos.getTurnos());
            System.out.println("Turno creado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al crear el turno: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos: " + e.getMessage());
        }
    }

    private static void verTurnos(GestorTurnos gestorTurnos) {
        System.out.println("--------------------------------------------------------");
        System.out.println("LISTA DE TURNOS");
        System.out.println("--------------------------------------------------------");

        Map<Integer, Turno> turnos = gestorTurnos.getTurnos();
        String format = "| %-4s | %-20s | %-20s | %-20s | %-10s | %-10s |%n";
        System.out.format("+------+----------------------+----------------------+----------------------+------------+------------+%n");
        System.out.format("| ID   | Empleado             | Inicio               | Fin                  | Horario    | Días       |%n");
        System.out.format("+------+----------------------+----------------------+----------------------+------------+------------+%n");

        for (Turno turno : turnos.values()) {
            String dias = turno.getDiasLaborables().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            System.out.format(format, turno.getId(), turno.getEmpleado().getNombre(), turno.getHoraInicio(), turno.getHoraFin(), turno.getHorario(), dias);
        }

        System.out.format("+------+----------------------+----------------------+----------------------+------------+------------+%n");
    }

    private static void actualizarTurno(GestorTurnos gestorTurnos, Scanner scanner) {
        System.out.print("Ingrese el ID del turno a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Turno turno = gestorTurnos.obtenerTurnoPorId(id);
        if (turno != null) {
            System.out.print("Ingrese la nueva hora de inicio del turno (HH:mm): ");
            String inicio = scanner.nextLine();
            System.out.print("Ingrese la nueva hora de fin del turno (HH:mm): ");
            String fin = scanner.nextLine();
            System.out.print("Ingrese los nuevos días laborables (1=Lunes, 7=Domingo, separados por ';', ej. 1;2;3): ");
            String dias = scanner.nextLine();
            System.out.print("Ingrese el nuevo horario (mañana/tarde): ");
            String horario = scanner.nextLine();

            Set<Integer> diasLaborables = Stream.of(dias.split(";"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());

            gestorTurnos.actualizarTurno(id, LocalTime.parse(inicio), LocalTime.parse(fin), diasLaborables, horario);
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

        Turno turno = gestorTurnos.obtenerTurnoPorId(id);
        if (turno != null) {
            gestorTurnos.eliminarTurno(id);
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
