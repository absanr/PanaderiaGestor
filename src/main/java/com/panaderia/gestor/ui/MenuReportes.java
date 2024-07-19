package com.panaderia.gestor.ui;

import com.panaderia.gestor.service.GestorAsistencia;
import com.panaderia.gestor.service.GestorTurnos;

import java.util.Scanner;

public class MenuReportes {
    public static void mostrarMenu(GestorAsistencia gestorAsistencia, GestorTurnos gestorTurnos) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("--------------------------------------------------------");
            System.out.println("MENÚ DE REPORTES");
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
                        generarReporteAsistencia(gestorAsistencia);
                        break;
                    case 2:
                        generarReporteTurnos(gestorTurnos);
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
        // Aquí puedes añadir la lógica para generar un reporte de asistencia.
        // Por ejemplo, podrías recorrer la lista de asistencias y mostrar los datos en un formato específico.
        System.out.println("Generando reporte de asistencia...");
        // ...
        System.out.println("Reporte de asistencia generado.");
    }

    private static void generarReporteTurnos(GestorTurnos gestorTurnos) {
        // Aquí puedes añadir la lógica para generar un reporte de turnos.
        // Por ejemplo, podrías recorrer la lista de turnos y mostrar los datos en un formato específico.
        System.out.println("Generando reporte de turnos...");
        // ...
        System.out.println("Reporte de turnos generado.");
    }
}
