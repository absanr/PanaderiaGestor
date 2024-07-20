package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.*;
import com.panaderia.gestor.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuPrincipal {
    private static final GestorTurnos gestorTurnos = new GestorTurnos();
    private static final GestorAsistencia gestorAsistencia = new GestorAsistencia();
    private static final AuthService authService = new AuthService();
    private static final GestorZonas gestorZonas = new GestorZonas();
    private static final GestorVentas gestorVentas = new GestorVentas();
    private static final Logger logger = Logger.getLogger(MenuPrincipal.class.getName());
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
                            if (tieneAcceso(Roles.ADMINISTRADOR, Roles.MANAGER)) {
                                mostrarSubMenuNegocio();
                            } else {
                                System.out.println("No tiene permisos para acceder a esta sección.");
                            }
                            break;
                        case 2:
                            if (tieneAcceso(Roles.ADMINISTRADOR, Roles.CAJERO)) {
                                mostrarSubMenuVentas();
                            } else {
                                System.out.println("No tiene permisos para acceder a esta sección.");
                            }
                            break;
                        case 3:
                            if (tieneAcceso(Roles.ADMINISTRADOR, Roles.MANAGER, Roles.REPOSTERO)) {
                                mostrarSubMenuInventario();
                            } else {
                                System.out.println("No tiene permisos para acceder a esta sección.");
                            }
                            break;
                        case 4:
                            if (tieneAcceso(Roles.ADMINISTRADOR, Roles.MANAGER, Roles.CAJERO)) {
                                MenuReportes.mostrarMenu(gestorAsistencia, gestorTurnos, gestorVentas);
                            } else {
                                System.out.println("No tiene permisos para acceder a esta sección.");
                            }
                            break;
                        case 5:
                            if (tieneAcceso(Roles.ADMINISTRADOR, Roles.MANAGER)) {
                                mostrarSubMenuCapitalPagos();
                            } else {
                                System.out.println("No tiene permisos para acceder a esta sección.");
                            }
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
        List<Producto> productos = DataLoader.cargarProductos();
        List<Venta> ventas = DataLoader.cargarVentas(productos);

        authService.setUsuarios(usuarios);
        gestorTurnos.setEmpleados(empleados);
        gestorTurnos.setTurnos(turnos);
        gestorAsistencia.setAsistencia(asistencia);
        gestorZonas.setZonas(zonas);
        gestorVentas.setProductos(productos);
        gestorVentas.setVentas(ventas);
        gestorVentas.setEmpleados(empleados); // Asegúrate de cargar los empleados en GestorVentas
        gestorVentas.cargarDatosNegocio();
    }

    private static void guardarDatos() {
        try {
            DataLoader.guardarUsuarios(authService.getUsuarios());
            DataLoader.guardarEmpleados(gestorTurnos.getEmpleados());
            DataLoader.guardarTurnos(gestorTurnos.obtenerTodosLosTurnos());
            DataLoader.guardarAsistencia(gestorAsistencia.getAsistencia());
            DataLoader.guardarZonas(gestorZonas.obtenerTodasLasZonas());
            DataLoader.guardarProductos(gestorVentas.getProductos());
            DataLoader.guardarVentas(gestorVentas.getVentas());
            gestorVentas.guardarDatosNegocio();
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
            logger.info("Login exitoso. Usuario: " + usuarioActual.getUsername());
            System.out.println("Login exitoso. Bienvenido, " + usuarioActual.getUsername() + "!");
            return true;
        } else {
            logger.warning("Login fallido. Usuario: " + username);
            System.out.println("Login fallido. Verifique sus credenciales.");
            return false;
        }
    }

    private static void mostrarFechasDePago() {
        if (tieneAcceso(Roles.ADMINISTRADOR, Roles.MANAGER)) {
            LocalDate hoy = LocalDate.now();
            gestorTurnos.getEmpleados().values().forEach(empleado -> {
                LocalDate proximaFechaPago = hoy.withDayOfMonth(30);  // Asumiendo que el pago es el último día del mes
                if (hoy.isAfter(proximaFechaPago)) {
                    proximaFechaPago = proximaFechaPago.plusMonths(1);
                }
                System.out.println("Próxima fecha de pago para " + empleado.getNombre() + ": " + proximaFechaPago);
            });
        } else {
            System.out.println("No tiene permisos para ver las fechas de pago.");
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("--------------------------------------------------------");
        System.out.println("MENU PRINCIPAL");
        System.out.println("--------------------------------------------------------");
        if (tieneAcceso(Roles.ADMINISTRADOR, Roles.MANAGER)) {
            System.out.println("1. Negocio");
        }
        if (tieneAcceso(Roles.ADMINISTRADOR, Roles.CAJERO)) {
            System.out.println("2. Ventas");
        }
        if (tieneAcceso(Roles.ADMINISTRADOR, Roles.MANAGER, Roles.REPOSTERO)) {
            System.out.println("3. Inventario");
        }
        if (tieneAcceso(Roles.ADMINISTRADOR, Roles.MANAGER, Roles.CAJERO)) {
            System.out.println("4. Reportes");
        }
        if (tieneAcceso(Roles.ADMINISTRADOR, Roles.MANAGER)) {
            System.out.println("5. Capital y Pagos");
        }
        System.out.println("0. Salir");
        System.out.println("--------------------------------------------------------");
        System.out.print("Ingrese opción [0-5]: ");
    }

    private static boolean tieneAcceso(String... rolesPermitidos) {
        for (String rolPermitido : rolesPermitidos) {
            if (usuarioActual.getRol().equals(rolPermitido)) {
                return true;
            }
        }
        return false;
    }

    private static void mostrarSubMenuNegocio() {
        boolean salir = false;
        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("MENÚ DE NEGOCIO");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Gestión de Empleados");
            System.out.println("2. Gestión de Turnos");
            System.out.println("3. Gestión de Asistencia");
            System.out.println("4. Gestión de Zonas");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-4]: ");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        MenuGestionEmpleados.mostrarMenu(gestorTurnos);
                        break;
                    case 2:
                        MenuGestionTurnos.mostrarMenu(gestorTurnos);
                        break;
                    case 3:
                        MenuGestionAsistencia.mostrarMenu(gestorAsistencia, gestorTurnos);
                        break;
                    case 4:
                        MenuGestionZonas.mostrarMenu(gestorZonas);
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

    private static void mostrarSubMenuVentas() {
        boolean salir = false;
        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("MENÚ DE VENTAS");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Registrar Venta");
            System.out.println("2. Ver Ventas");
            System.out.println("3. Total de Ventas del Día");
            System.out.println("4. Total de Ventas de Otro Día");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-4]: ");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        MenuVentas.mostrarMenu(gestorVentas);
                        break;
                    case 2:
                        MenuVentas.mostrarVentas(gestorVentas);
                        break;
                    case 3:
                        MenuVentas.mostrarTotalVentasDelDia(gestorVentas);
                        break;
                    case 4:
                        MenuVentas.mostrarTotalVentasDeOtroDia(gestorVentas, scanner);
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

    private static void mostrarSubMenuInventario() {
        boolean salir = false;
        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("MENÚ DE INVENTARIO");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Gestión de Productos");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-1]: ");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        MenuProductos.mostrarMenu(gestorVentas);
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

    private static void mostrarSubMenuCapitalPagos() {
        boolean salir = false;
        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("MENÚ DE CAPITAL Y PAGOS");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Ver Capital del Negocio");
            System.out.println("2. Inyectar Capital");
            System.out.println("3. Pagar a Empleado");
            System.out.println("4. Ver Pagos Realizados");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Ingrese opción [0-4]: ");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        MenuCapital.verCapital(gestorVentas);
                        break;
                    case 2:
                        MenuCapital.inyectarCapital(gestorVentas, scanner);
                        break;
                    case 3:
                        MenuCapital.pagarEmpleado(gestorVentas, scanner);
                        break;
                    case 4:
                        MenuCapital.verPagosRealizados(gestorVentas, scanner);
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
}
