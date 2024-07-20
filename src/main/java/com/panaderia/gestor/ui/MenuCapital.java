package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.EmpleadoPago;
import com.panaderia.gestor.service.GestorVentas;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuCapital {
    public static void mostrarMenu(GestorVentas gestorVentas) {
        Scanner scanner = new Scanner(System.in);
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
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-4]: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    verCapital(gestorVentas);
                    break;
                case 2:
                    inyectarCapital(gestorVentas, scanner);
                    break;
                case 3:
                    pagarEmpleado(gestorVentas, scanner);
                    break;
                case 4:
                    verPagosRealizados(gestorVentas, scanner);
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }
    }

    public static void verCapital(GestorVentas gestorVentas) {
        System.out.println("--------------------------------------------------------");
        System.out.println("CAPITAL DEL NEGOCIO");
        System.out.println("--------------------------------------------------------");
        System.out.println("Capital actual: S/" + gestorVentas.getCapitalActual());
    }

    public static void inyectarCapital(GestorVentas gestorVentas, Scanner scanner) {
        System.out.print("Ingrese el monto a inyectar: ");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Consumir la nueva línea

        gestorVentas.inyectarCapital(monto);
        System.out.println("Capital inyectado correctamente.");
    }

    public static void pagarEmpleado(GestorVentas gestorVentas, Scanner scanner) {
        System.out.print("Ingrese el ID del empleado: ");
        int empleadoId = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Empleado empleado = gestorVentas.obtenerEmpleadoPorId(empleadoId);
        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        System.out.println("Empleado seleccionado: " + empleado.getNombre() + " - Sueldo: S/" + empleado.getSueldo());

        System.out.print("Ingrese el tipo de pago (total/porcentaje/fijo): ");
        String tipoPago = scanner.nextLine();

        double monto = 0.0;

        if (tipoPago.equalsIgnoreCase("porcentaje")) {
            System.out.print("Ingrese el porcentaje a pagar: ");
            double porcentaje = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea
            monto = porcentaje;
        } else if (tipoPago.equalsIgnoreCase("fijo")) {
            System.out.print("Ingrese el monto fijo a pagar: ");
            monto = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea
            if (monto > empleado.getSueldo()) {
                System.out.println("El monto fijo no puede ser mayor al sueldo del empleado.");
                return;
            }
        } else if (tipoPago.equalsIgnoreCase("total")) {
            monto = empleado.getSueldo();
        } else {
            System.out.println("Tipo de pago no válido.");
            return;
        }

        gestorVentas.pagarEmpleado(empleadoId, monto, tipoPago);
        System.out.println("Pago realizado correctamente.");
    }

    public static void verPagosRealizados(GestorVentas gestorVentas, Scanner scanner) {
        System.out.print("¿Desea ver todos los pagos o por rango de fechas? (todos/rango): ");
        String opcion = scanner.nextLine();

        List<EmpleadoPago> pagos;
        if (opcion.equalsIgnoreCase("rango")) {
            System.out.print("Ingrese la fecha de inicio (yyyy-MM-dd): ");
            LocalDate fechaInicio = LocalDate.parse(scanner.nextLine());
            System.out.print("Ingrese la fecha de fin (yyyy-MM-dd): ");
            LocalDate fechaFin = LocalDate.parse(scanner.nextLine());
            pagos = gestorVentas.obtenerPagosPorRangoFechas(fechaInicio, fechaFin);
        } else {
            pagos = gestorVentas.getPagos();
        }

        System.out.println("--------------------------------------------------------");
        System.out.println("PAGOS REALIZADOS");
        System.out.println("--------------------------------------------------------");
        String format = "| %-4s | %-20s | %-10s | %-10s | %-10s |%n";
        System.out.format("+------+----------------------+------------+------------+------------+%n");
        System.out.format("| ID   | Empleado             | Fecha      | Monto      | Estado     |%n");
        System.out.format("+------+----------------------+------------+------------+------------+%n");

        for (EmpleadoPago pago : pagos) {
            Empleado empleado = gestorVentas.obtenerEmpleadoPorId(pago.getEmpleadoId());
            System.out.format(format, pago.getEmpleadoId(), empleado.getNombre(), pago.getFechaPago(), pago.getMonto(), pago.getEstado());
        }

        System.out.format("+------+----------------------+------------+------------+------------+%n");
    }
}
