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
    private static final Scanner scanner = new Scanner(System.in);

    public static void mostrarMenu(GestorTurnos gestorTurnos) {
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
                        crearTurno(gestorTurnos);
                        break;
                    case 2:
                        verTurnos(gestorTurnos);
                        break;
                    case 3:
                        actualizarTurno(gestorTurnos);
                        break;
                    case 4:
                        eliminarTurno(gestorTurnos);
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

    private static void crearTurno(GestorTurnos gestorTurnos) {
        try {
            int id = IdGenerator.obtenerSiguienteId("turnos.txt");
            System.out.print("Ingrese el ID del empleado: ");
            int empleadoId = leerNumero();
            System.out.print("Ingrese la hora de inicio del turno (HH:mm): ");
            LocalTime horaInicio = leerHora();
            System.out.print("Ingrese la hora de fin del turno (HH:mm): ");
            LocalTime horaFin = leerHora();
            Set<Integer> diasLaborables = leerDiasLaborables();
            String horario = leerHorario();

            gestorTurnos.agregarTurno(id, gestorTurnos.obtenerEmpleadoPorId(empleadoId), horaInicio, horaFin, diasLaborables, horario);
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

    private static void actualizarTurno(GestorTurnos gestorTurnos) {
        System.out.print("Ingrese el ID del turno a actualizar: ");
        int id = leerNumero();

        Turno turno = gestorTurnos.obtenerTurnoPorId(id);
        if (turno != null) {
            System.out.print("Ingrese la nueva hora de inicio del turno (HH:mm): ");
            LocalTime horaInicio = leerHora();
            System.out.print("Ingrese la nueva hora de fin del turno (HH:mm): ");
            LocalTime horaFin = leerHora();
            Set<Integer> diasLaborables = leerDiasLaborables();
            String horario = leerHorario();

            gestorTurnos.actualizarTurno(id, horaInicio, horaFin, diasLaborables, horario);
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

    private static void eliminarTurno(GestorTurnos gestorTurnos) {
        System.out.print("Ingrese el ID del turno a eliminar: ");
        int id = leerNumero();

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

    private static int leerNumero() {
        while (true) {
            try {
                int numero = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpiar la entrada inválida
            }
        }
    }

    private static LocalTime leerHora() {
        while (true) {
            try {
                String hora = scanner.nextLine();
                return LocalTime.parse(hora);
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, ingrese una hora válida (HH:mm).");
            }
        }
    }

    private static Set<Integer> leerDiasLaborables() {
        while (true) {
            System.out.print("Seleccione los días laborables: (1) Lunes, (2) Martes, (3) Miércoles, (4) Jueves, (5) Viernes, (6) Sábado, (7) Domingo, (8) Todos: ");
            String dias = scanner.nextLine();
            try {
                if (dias.equals("8")) {
                    return Stream.of(1, 2, 3, 4, 5, 6, 7).collect(Collectors.toSet());
                } else {
                    return Stream.of(dias.split(";"))
                            .map(Integer::parseInt)
                            .filter(d -> d >= 1 && d <= 7)
                            .collect(Collectors.toSet());
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, ingrese los días en el formato correcto.");
            }
        }
    }

    private static String leerHorario() {
        while (true) {
            System.out.print("Seleccione el horario: (1) Mañana, (2) Tarde, (3) Otro: ");
            String horario = scanner.nextLine();
            switch (horario) {
                case "1":
                    return "Maniana";
                case "2":
                    return "Tarde";
                case "3":
                    System.out.print("Ingrese el nombre del horario: ");
                    return scanner.nextLine();
                default:
                    System.out.println("Entrada inválida. Por favor, ingrese una opción válida.");
            }
        }
    }
}
