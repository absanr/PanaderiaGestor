package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.Turno;
import com.panaderia.gestor.model.Usuario;
import com.panaderia.gestor.model.Zona;
import com.panaderia.gestor.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {
    private static final GestorTurnos gestorTurnos = new GestorTurnos();
    private static final GestorAsistencia gestorAsistencia = new GestorAsistencia();
    private static final AuthService authService = new AuthService();
    private static final GestorZonas gestorZonas = new GestorZonas();
    private static final Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual;

    public static void main(String[] args) {
        try {
            DataLoader.verificarOCrearArchivos();
            if (!ConfiguracionInicial.configuracionCompleta()) {
                System.out.println("Bienvenido al sistema de gestión de la panadería. Se detectó una configuración incompleta.");
                ConfiguracionInicial.configurarSistema(scanner);
            }
            cargarDatosIniciales();
        } catch (IOException e) {
            System.err.println("Error al cargar los datos iniciales: " + e.getMessage());
            return;
        }

        if (login()) {
            mostrarFechasDePago();
            boolean salir = false;
            while (!salir) {
                mostrarMenuPrincipal();
                try {
                    int opcion = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea
                    switch (opcion) {
                        case 1:
                            MenuGestionTurnos.mostrarMenu(gestorTurnos);
                            break;
                        case 2:
                            MenuGestionEmpleados.mostrarMenu(gestorTurnos);
                            break;
                        case 3:
                            MenuGestionAsistencia.mostrarMenu(gestorAsistencia, gestorTurnos);
                            break;
                        case 4:
                            MenuGestionZonas.mostrarMenu(gestorZonas);
                            break;
                        case 5:
                            MenuReportes.mostrarMenu(gestorAsistencia, gestorTurnos);
                            break;
                        case 0:
                            salir = true;
                            guardarDatos();
                            break;
                        default:
                            System.out.println("Opción no válida. Por favor, intente nuevamente.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, ingrese un número.");
                    scanner.nextLine(); // Limpiar la entrada inválida
                }
            }
        } else {
            System.out.println("Autenticación fallida. Saliendo del sistema.");
        }
    }

    private static void cargarDatosIniciales() throws IOException {
        Map<String, Usuario> usuarios = DataLoader.cargarUsuarios();
        Map<Integer, Empleado> empleados = DataLoader.cargarEmpleados();
        Map<Integer, Turno> turnos = DataLoader.cargarTurnos(empleados);
        Map<Integer, Map<LocalDate, String>> asistencia = DataLoader.cargarAsistencia(empleados);
        Map<Integer, Zona> zonas = DataLoader.cargarZonas();

        authService.setUsuarios(usuarios);
        gestorTurnos.setEmpleados(empleados);
        gestorTurnos.setTurnos(turnos);
        gestorAsistencia.setAsistencia(asistencia);
        gestorZonas.setZonas(zonas);
    }

    private static void guardarDatos() {
        try {
            DataLoader.guardarUsuarios(authService.getUsuarios());
            DataLoader.guardarEmpleados(gestorTurnos.getEmpleados());
            DataLoader.guardarTurnos(gestorTurnos.obtenerTodosLosTurnos());
            DataLoader.guardarAsistencia(gestorAsistencia.getAsistencia());
            DataLoader.guardarZonas(gestorZonas.obtenerTodasLasZonas());
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    private static boolean login() {
        System.out.print("Ingrese su nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        usuarioActual = authService.login(username, password);

        if (usuarioActual != null) {
            System.out.println("Login exitoso. Bienvenido, " + usuarioActual.getUsername() + "!");
            return true;
        } else {
            System.out.println("Login fallido. Verifique sus credenciales.");
            return false;
        }
    }

    private static void mostrarFechasDePago() {
        LocalDate hoy = LocalDate.now();
        gestorTurnos.getEmpleados().values().forEach(empleado -> {
            LocalDate proximaFechaPago = hoy.withDayOfMonth(30);  // Asumiendo que el pago es el último día del mes
            if (hoy.isAfter(proximaFechaPago)) {
                proximaFechaPago = proximaFechaPago.plusMonths(1);
            }
            System.out.println("Próxima fecha de pago para " + empleado.getNombre() + ": " + proximaFechaPago);
        });
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("--------------------------------------------------------");
        System.out.println("MENU PRINCIPAL");
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Gestión de Turnos");
        System.out.println("2. Gestión de Empleados");
        System.out.println("3. Gestión de Asistencia");
        System.out.println("4. Gestión de Zonas");
        System.out.println("5. Reportes");
        System.out.println("0. Salir");
        System.out.println("--------------------------------------------------------");
        System.out.print("Ingrese opción [0-5]: ");
    }
}
