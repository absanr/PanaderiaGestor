package com.panaderia.gestor.ui;

import com.panaderia.gestor.service.GestorAsistencia;
import com.panaderia.gestor.service.GestorTurnos;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuReportes {
    public static void mostrarMenu(GestorAsistencia gestorAsistencia, GestorTurnos gestorTurnos) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("GENERAR REPORTES");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Reporte de Asistencia");
            System.out.println("2. Reporte de Turnos");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-2]: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                switch (opcion) {
                    case 1:
                        ReporteGenerator.generarReporteAsistencia(gestorAsistencia);
                        break;
                    case 2:
                        ReporteGenerator.generarReporteTurnos(gestorTurnos);
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
