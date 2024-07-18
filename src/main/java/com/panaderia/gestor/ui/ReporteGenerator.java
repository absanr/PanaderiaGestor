package com.panaderia.gestor.ui;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.Turno;
import com.panaderia.gestor.service.GestorAsistencia;
import com.panaderia.gestor.service.GestorTurnos;

import java.time.LocalDate;
import java.util.Map;

public class ReporteGenerator {

    public static void generarReporteAsistencia(GestorAsistencia gestorAsistencia) {
        System.out.println("--------------------------------------------------------");
        System.out.println("REPORTE DE ASISTENCIA");
        System.out.println("--------------------------------------------------------");

        Map<Integer, Map<LocalDate, String>> asistencia = gestorAsistencia.getAsistencia();
        String format = "| %-10s | %-10s | %-6s |%n";
        System.out.format("+------------+------------+--------+%n");
        System.out.format("| EmpleadoID | Fecha      | Estado |%n");
        System.out.format("+------------+------------+--------+%n");

        for (Map.Entry<Integer, Map<LocalDate, String>> entry : asistencia.entrySet()) {
            int empleadoId = entry.getKey();
            Map<LocalDate, String> asistencias = entry.getValue();
            for (Map.Entry<LocalDate, String> asistenciaEntry : asistencias.entrySet()) {
                System.out.format(format, empleadoId, asistenciaEntry.getKey(), asistenciaEntry.getValue());
            }
        }

        System.out.format("+------------+------------+--------+%n");
    }

    public static void generarReporteTurnos(GestorTurnos gestorTurnos) {
        System.out.println("--------------------------------------------------------");
        System.out.println("REPORTE DE TURNOS");
        System.out.println("--------------------------------------------------------");

        Map<Integer, Turno> turnos = gestorTurnos.obtenerTodosLosTurnos();
        String format = "| %-4s | %-20s | %-20s | %-20s |%n";
        System.out.format("+------+----------------------+----------------------+----------------------+%n");
        System.out.format("| ID   | Empleado             | Inicio               | Fin                  |%n");
        System.out.format("+------+----------------------+----------------------+----------------------+%n");

        for (Turno turno : turnos.values()) {
            System.out.format(format, turno.getId(), turno.getEmpleado().getNombre(), turno.getInicio(), turno.getFin());
        }

        System.out.format("+------+----------------------+----------------------+----------------------+%n");
    }
}
