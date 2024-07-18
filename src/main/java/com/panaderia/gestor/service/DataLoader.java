package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.Turno;
import com.panaderia.gestor.model.Usuario;
import com.panaderia.gestor.model.Zona;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DataLoader {

    private static final String BASE_PATH = "src/main/resources/";

    public static void verificarOCrearArchivos() throws IOException {
        String[] archivos = {"usuarios.txt", "empleados.txt", "turnos.txt", "asistencia.txt", "zonas.txt"};
        for (String archivo : archivos) {
            File file = new File(BASE_PATH + archivo);
            if (!file.exists()) {
                file.createNewFile();
            }
        }
    }

    public static boolean hayDatosEnArchivo(String nombreArchivo) throws IOException {
        File file = new File(BASE_PATH + nombreArchivo);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                return br.readLine() != null;
            }
        }
        return false;
    }

    public static Map<String, Usuario> cargarUsuarios() throws IOException {
        Map<String, Usuario> usuarios = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 3) {
                    usuarios.put(parts[0], new Usuario(parts[0], parts[1], parts[2]));
                }
            }
        }
        return usuarios;
    }

    public static void guardarUsuarios(Map<String, Usuario> usuarios) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "usuarios.txt"))) {
            for (Usuario usuario : usuarios.values()) {
                bw.write(usuario.getUsername() + "," + usuario.getPassword() + "," + usuario.getRole());
                bw.newLine();
            }
        }
    }

    public static Map<Integer, Empleado> cargarEmpleados() throws IOException {
        Map<Integer, Empleado> empleados = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "empleados.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    double sueldo = Double.parseDouble(parts[3]);
                    empleados.put(id, new Empleado(id, parts[1], parts[2], sueldo));
                }
            }
        }
        return empleados;
    }

    public static void guardarEmpleados(Map<Integer, Empleado> empleados) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "empleados.txt"))) {
            for (Map.Entry<Integer, Empleado> entry : empleados.entrySet()) {
                Empleado empleado = entry.getValue();
                bw.write(entry.getKey() + "," + empleado.getNombre() + "," + empleado.getRol() + "," + empleado.getSueldo());
                bw.newLine();
            }
        }
    }

    public static Map<Integer, Turno> cargarTurnos(Map<Integer, Empleado> empleados) throws IOException {
        Map<Integer, Turno> turnos = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "turnos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    Empleado empleado = empleados.get(Integer.parseInt(parts[1]));
                    if (empleado != null) {
                        Turno turno = new Turno(id, empleado, LocalDateTime.parse(parts[2]), LocalDateTime.parse(parts[3]));
                        turnos.put(id, turno);
                    }
                }
            }
        }
        return turnos;
    }

    public static void guardarTurnos(Map<Integer, Turno> turnos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "turnos.txt"))) {
            for (Turno turno : turnos.values()) {
                bw.write(turno.getId() + "," + turno.getEmpleado().getId() + "," + turno.getInicio() + "," + turno.getFin());
                bw.newLine();
            }
        }
    }

    public static Map<Integer, Map<LocalDate, String>> cargarAsistencia(Map<Integer, Empleado> empleados) throws IOException {
        Map<Integer, Map<LocalDate, String>> asistencia = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "asistencia.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 3) {
                    int empleadoId = Integer.parseInt(parts[0]);
                    LocalDate fecha = LocalDate.parse(parts[1]);
                    String estado = parts[2];
                    asistencia.putIfAbsent(empleadoId, new HashMap<>());
                    asistencia.get(empleadoId).put(fecha, estado);
                }
            }
        }
        return asistencia;
    }

    public static void guardarAsistencia(Map<Integer, Map<LocalDate, String>> asistencia) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "asistencia.txt"))) {
            for (Map.Entry<Integer, Map<LocalDate, String>> entry : asistencia.entrySet()) {
                int empleadoId = entry.getKey();
                for (Map.Entry<LocalDate, String> asistenciaEntry : entry.getValue().entrySet()) {
                    bw.write(empleadoId + "," + asistenciaEntry.getKey() + "," + asistenciaEntry.getValue());
                    bw.newLine();
                }
            }
        }
    }

    public static Map<Integer, Zona> cargarZonas() throws IOException {
        Map<Integer, Zona> zonas = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "zonas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    zonas.put(id, new Zona(id, parts[1], parts[2]));
                }
            }
        }
        return zonas;
    }

    public static void guardarZonas(Map<Integer, Zona> zonas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "zonas.txt"))) {
            for (Map.Entry<Integer, Zona> entry : zonas.entrySet()) {
                Zona zona = entry.getValue();
                bw.write(entry.getKey() + "," + zona.getNombre() + "," + zona.getDescripcion());
                bw.newLine();
            }
        }
    }
}
