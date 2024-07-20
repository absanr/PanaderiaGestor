package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.Venta;
import com.panaderia.gestor.service.GestorAsistencia;
import com.panaderia.gestor.service.GestorTurnos;
import com.panaderia.gestor.service.GestorVentas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuReportes {
    public static void mostrarMenu(GestorAsistencia gestorAsistencia, GestorTurnos gestorTurnos, GestorVentas gestorVentas) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("MENÚ DE REPORTES");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Reporte de Asistencia");
            System.out.println("2. Reporte de Turnos");
            System.out.println("3. Reporte de Pagos");
            System.out.println("4. Reporte de Ventas");
            System.out.println("5. Generar Reporte de Gastos vs Ganancias");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-5]: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        generarReporteAsistencia(gestorAsistencia);
                        break;
                    case 2:
                        generarReporteTurnos(gestorTurnos);
                        break;
                    case 3:
                        previsualizarPagoEmpleado(gestorAsistencia, gestorTurnos, scanner);
                        break;
                    case 4:
                        generarReporteVentas(gestorVentas);
                        break;
                    case 5:
                        generarReporteGastosVsGanancias(gestorVentas, scanner);
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpiar la entrada inválida
            }
        }
    }

    private static void generarReporteAsistencia(GestorAsistencia gestorAsistencia) {
        System.out.println("Generando reporte de asistencia...");
        // Lógica para generar reporte de asistencia
        System.out.println("Reporte de asistencia generado.");
    }

    private static void generarReporteTurnos(GestorTurnos gestorTurnos) {
        System.out.println("Generando reporte de turnos...");
        // Lógica para generar reporte de turnos
        System.out.println("Reporte de turnos generado.");
    }

    private static void previsualizarPagoEmpleado(GestorAsistencia gestorAsistencia, GestorTurnos gestorTurnos, Scanner scanner) {
        System.out.print("Ingrese el ID del empleado para previsualizar el pago: ");
        int empleadoId = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        Empleado empleado = gestorTurnos.obtenerEmpleadoPorId(empleadoId);
        if (empleado != null) {
            double sueldo = empleado.getSueldo();
            double deducciones = calcularDeducciones(gestorAsistencia.getAsistenciaPorEmpleado(empleadoId));
            double pagoNeto = sueldo - deducciones;
            LocalDate fechaPago = LocalDate.now().withDayOfMonth(30);

            System.out.println("Previsualización de pago:");
            System.out.println("Empleado: " + empleado.getNombre());
            System.out.println("Sueldo: " + sueldo);
            System.out.println("Deducciones: " + deducciones);
            System.out.println("Pago Neto: " + pagoNeto);
            System.out.println("Fecha de Pago: " + fechaPago);
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private static double calcularDeducciones(Map<LocalDate, String> asistenciaEmpleado) {
        double deducciones = 0.0;
        if (asistenciaEmpleado != null) {
            for (String estado : asistenciaEmpleado.values()) {
                if (estado.equals("AUSENTE")) {
                    deducciones += 50.0; // Porcentaje de deducción por ausencia
                } else if (estado.equals("TARDE")) {
                    deducciones += 25.0; // Porcentaje de deducción por tardanza
                }
            }
        }
        return deducciones;
    }

    private static void generarReporteVentas(GestorVentas gestorVentas) {
        System.out.println("Generando reporte de ventas...");

        List<Venta> ventas = gestorVentas.getVentas();
        String format = "| %-4s | %-20s | %-10s | %-10s | %-20s |%n";
        System.out.format("+------+----------------------+------------+------------+----------------------+%n");
        System.out.format("| ID   | Producto             | Cantidad   | Total      | Fecha                |%n");
        System.out.format("+------+----------------------+------------+------------+----------------------+%n");

        for (Venta venta : ventas) {
            System.out.format(format, venta.getId(), venta.getProducto().getNombre(), venta.getCantidad(), venta.getTotal(), venta.getFecha());
        }

        System.out.format("+------+----------------------+------------+------------+----------------------+%n");
        guardarReporteVentas(ventas);
        System.out.println("Reporte de ventas generado.");
    }

    private static void guardarReporteVentas(List<Venta> ventas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/reporte_ventas.txt"))) {
            for (Venta venta : ventas) {
                bw.write(venta.getId() + "," + venta.getProducto().getNombre() + "," + venta.getCantidad() + "," + venta.getTotal() + "," + venta.getFecha());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el reporte de ventas: " + e.getMessage());
        }
    }

    private static void generarReporteGastosVsGanancias(GestorVentas gestorVentas, Scanner scanner) {
        System.out.print("Ingrese la fecha de inicio del reporte (yyyy-MM-dd): ");
        LocalDate fechaInicio = LocalDate.parse(scanner.nextLine());
        System.out.print("Ingrese la fecha de fin del reporte (yyyy-MM-dd): ");
        LocalDate fechaFin = LocalDate.parse(scanner.nextLine());

        gestorVentas.generarReporteGastosVsGanancias(fechaInicio, fechaFin);
    }
}
