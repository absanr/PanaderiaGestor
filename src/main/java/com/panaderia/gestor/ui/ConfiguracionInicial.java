package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.Turno;
import com.panaderia.gestor.model.Usuario;
import com.panaderia.gestor.model.Zona;
import com.panaderia.gestor.service.DataLoader;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfiguracionInicial {

    private static final String USUARIOS_FILE = "usuarios.txt";
    private static final String EMPLEADOS_FILE = "empleados.txt";
    private static final String TURNOS_FILE = "turnos.txt";
    private static final String ASISTENCIA_FILE = "asistencia.txt";
    private static final String ZONAS_FILE = "zonas.txt";

    public static boolean configuracionCompleta() throws IOException {
        boolean usuarios = DataLoader.hayDatosEnArchivo(USUARIOS_FILE);
        boolean empleados = DataLoader.hayDatosEnArchivo(EMPLEADOS_FILE);
        boolean turnos = DataLoader.hayDatosEnArchivo(TURNOS_FILE);
        boolean asistencia = DataLoader.hayDatosEnArchivo(ASISTENCIA_FILE);
        boolean zonas = DataLoader.hayDatosEnArchivo(ZONAS_FILE);

        if (!usuarios) {
            System.out.println("Falta configuración de usuarios.");
        }
        if (!empleados) {
            System.out.println("Falta configuración de empleados.");
        }
        if (!turnos) {
            System.out.println("Falta configuración de turnos.");
        }
        if (!asistencia) {
            System.out.println("Falta configuración de asistencia.");
        }
        if (!zonas) {
            System.out.println("Falta configuración de zonas.");
        }

        return usuarios && empleados && turnos && asistencia && zonas;
    }

    public static void configurarSistema(Scanner scanner) throws IOException {
        while (true) {
            System.out.println("Seleccione la opción para configurar:");
            System.out.println("1. Configurar usuarios");
            System.out.println("2. Configurar empleados");
            System.out.println("3. Configurar turnos");
            System.out.println("4. Configurar asistencia");
            System.out.println("5. Configurar zonas");
            System.out.println("6. Finalizar configuración");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    configurarUsuarios(scanner);
                    break;
                case 2:
                    configurarEmpleados(scanner);
                    break;
                case 3:
                    configurarTurnos(scanner);
                    break;
                case 4:
                    configurarAsistencia(scanner);
                    break;
                case 5:
                    configurarZonas(scanner);
                    break;
                case 6:
                    if (configuracionCompleta()) {
                        return;
                    } else {
                        System.out.println("La configuración está incompleta. Por favor, complete todas las configuraciones.");
                    }
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }
    }

    private static void configurarUsuarios(Scanner scanner) throws IOException {
        Map<String, Usuario> usuarios = DataLoader.cargarUsuarios();
        System.out.println("Ingrese los usuarios en el formato: username,password,role (ADMIN, USER, MANAGER, EMPLOYEE). Ingrese 'fin' para finalizar.");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }
            String[] parts = input.split(",");
            if (parts.length == 3) {
                usuarios.put(parts[0], new Usuario(parts[0], parts[1], parts[2]));
            } else {
                System.out.println("Formato inválido. Por favor, intente nuevamente.");
            }
        }
        DataLoader.guardarUsuarios(usuarios);
    }

    private static void configurarEmpleados(Scanner scanner) throws IOException {
        Map<Integer, Empleado> empleados = DataLoader.cargarEmpleados();
        System.out.println("Ingrese los empleados en el formato: id,nombre,rol,sueldo. Ingrese 'fin' para finalizar.");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }
            String[] parts = input.split(",");
            if (parts.length == 4) {
                int id = Integer.parseInt(parts[0]);
                double sueldo = Double.parseDouble(parts[3]);
                empleados.put(id, new Empleado(id, parts[1], parts[2], sueldo));
            } else {
                System.out.println("Formato inválido. Por favor, intente nuevamente.");
            }
        }
        DataLoader.guardarEmpleados(empleados);
    }

    private static void configurarTurnos(Scanner scanner) throws IOException {
        Map<Integer, Empleado> empleados = DataLoader.cargarEmpleados();
        Map<Integer, Turno> turnos = DataLoader.cargarTurnos(empleados);
        System.out.println("Ingrese los turnos en el formato: id,empleadoId,inicio,fin (yyyy-MM-ddTHH:mm). Ingrese 'fin' para finalizar.");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }
            String[] parts = input.split(",");
            if (parts.length == 4) {
                int id = Integer.parseInt(parts[0]);
                Empleado empleado = empleados.get(Integer.parseInt(parts[1]));
                if (empleado != null) {
                    Turno turno = new Turno(id, empleado, LocalDateTime.parse(parts[2]), LocalDateTime.parse(parts[3]));
                    turnos.put(id, turno);
                } else {
                    System.out.println("Empleado no encontrado. Por favor, intente nuevamente.");
                }
            } else {
                System.out.println("Formato inválido. Por favor, intente nuevamente.");
            }
        }
        DataLoader.guardarTurnos(turnos);
    }

    private static void configurarAsistencia(Scanner scanner) throws IOException {
        Map<Integer, Empleado> empleados = DataLoader.cargarEmpleados();
        Map<Integer, Map<LocalDate, String>> asistencia = DataLoader.cargarAsistencia(empleados);
        System.out.println("Ingrese la asistencia en el formato: empleadoId,fecha,estado (yyyy-MM-dd,true/false/tarde). Ingrese 'fin' para finalizar.");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }
            String[] parts = input.split(",");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[0]);
                LocalDate fecha = LocalDate.parse(parts[1]);
                String estado = parts[2];
                Empleado empleado = empleados.get(id);
                if (empleado != null) {
                    asistencia.putIfAbsent(id, new HashMap<>());
                    asistencia.get(id).put(fecha, estado);
                } else {
                    System.out.println("Empleado no encontrado. Por favor, intente nuevamente.");
                }
            } else {
                System.out.println("Formato inválido. Por favor, intente nuevamente.");
            }
        }
        DataLoader.guardarAsistencia(asistencia);
    }

    private static void configurarZonas(Scanner scanner) throws IOException {
        Map<Integer, Zona> zonas = DataLoader.cargarZonas();
        System.out.println("Ingrese las zonas en el formato: id,nombre,descripcion. Ingrese 'fin' para finalizar.");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }
            String[] parts = input.split(",");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[0]);
                zonas.put(id, new Zona(id, parts[1], parts[2]));
            } else {
                System.out.println("Formato inválido. Por favor, intente nuevamente.");
            }
        }
        DataLoader.guardarZonas(zonas);
    }
}
