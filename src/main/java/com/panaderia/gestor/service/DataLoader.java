package com.panaderia.gestor.service;

import com.panaderia.gestor.model.*;
import com.panaderia.gestor.util.LoggerConfig;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DataLoader {

    private static final String BASE_PATH = "src/main/resources/";
    private static final Logger logger = LoggerConfig.getLogger();

    public static void verificarOCrearArchivos() throws IOException {
        String[] archivos = {"usuarios.txt", "empleados.txt", "turnos.txt", "asistencia.txt", "zonas.txt", "negocio.txt", "productos.txt", "ventas.txt"};
        for (String archivo : archivos) {
            File file = new File(BASE_PATH + archivo);
            if (!file.exists()) {
                file.createNewFile();
                logger.info("Archivo creado: " + archivo);
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
        logger.info("Usuarios cargados.");
        return usuarios;
    }

    public static void guardarUsuarios(Map<String, Usuario> usuarios) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "usuarios.txt"))) {
            for (Usuario usuario : usuarios.values()) {
                bw.write(usuario.getUsername() + "," + usuario.getPassword() + "," + usuario.getRol());
                bw.newLine();
            }
        }
        logger.info("Usuarios guardados.");
    }

    public static Map<Integer, Empleado> cargarEmpleados() throws IOException {
        Map<Integer, Empleado> empleados = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "empleados.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 8) {
                    int id = Integer.parseInt(parts[0]);
                    double sueldo = Double.parseDouble(parts[3]);
                    LocalDate fechaNacimiento = LocalDate.parse(parts[4]);
                    LocalDate inicioContrato = LocalDate.parse(parts[5]);
                    LocalDate finContrato = LocalDate.parse(parts[6]);
                    LocalDate fechaPago = LocalDate.parse(parts[7]);
                    empleados.put(id, new Empleado(id, parts[1], parts[2], sueldo, fechaNacimiento, inicioContrato, finContrato, fechaPago));
                }
            }
        }
        logger.info("Empleados cargados.");
        return empleados;
    }

    public static void guardarEmpleados(Map<Integer, Empleado> empleados) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "empleados.txt"))) {
            for (Map.Entry<Integer, Empleado> entry : empleados.entrySet()) {
                Empleado empleado = entry.getValue();
                bw.write(entry.getKey() + "," + empleado.getNombre() + "," + empleado.getRol() + "," + empleado.getSueldo() + "," +
                        empleado.getFechaNacimiento() + "," + empleado.getInicioContrato() + "," + empleado.getFinContrato() + "," + empleado.getFechaPago());
                bw.newLine();
            }
        }
        logger.info("Empleados guardados.");
    }

    public static Map<Integer, Turno> cargarTurnos(Map<Integer, Empleado> empleados) throws IOException {
        Map<Integer, Turno> turnos = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "turnos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    Empleado empleado = empleados.get(Integer.parseInt(parts[1]));
                    if (empleado != null) {
                        Turno turno = new Turno(id, empleado, LocalDateTime.parse(parts[2]), LocalDateTime.parse(parts[3]), parts[4]);
                        turnos.put(id, turno);
                    }
                }
            }
        }
        logger.info("Turnos cargados.");
        return turnos;
    }

    public static void guardarTurnos(Map<Integer, Turno> turnos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "turnos.txt"))) {
            for (Turno turno : turnos.values()) {
                bw.write(turno.getId() + "," + turno.getEmpleado().getId() + "," + turno.getInicio() + "," + turno.getFin() + "," + turno.getHorario());
                bw.newLine();
            }
        }
        logger.info("Turnos guardados.");
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
        logger.info("Asistencias cargadas.");
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
        logger.info("Asistencias guardadas.");
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
        logger.info("Zonas cargadas.");
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
        logger.info("Zonas guardadas.");
    }

    public static List<Producto> cargarProductos() throws IOException {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "productos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    double precioVenta = Double.parseDouble(parts[2]);
                    double costoProduccion = Double.parseDouble(parts[3]);
                    int stock = Integer.parseInt(parts[4]);
                    productos.add(new Producto(id, parts[1], precioVenta, costoProduccion, stock));
                }
            }
        }
        logger.info("Productos cargados.");
        return productos;
    }

    public static void guardarProductos(List<Producto> productos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "productos.txt"))) {
            for (Producto producto : productos) {
                bw.write(producto.getId() + "," + producto.getNombre() + "," + producto.getPrecioVenta() + "," + producto.getCostoProduccion() + "," + producto.getStock());
                bw.newLine();
            }
        }
        logger.info("Productos guardados.");
    }

    public static List<Venta> cargarVentas(List<Producto> productos) throws IOException {
        List<Venta> ventas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "ventas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    Producto producto = productos.stream().filter(p -> p.getId() == Integer.parseInt(parts[1])).findFirst().orElse(null);
                    if (producto != null) {
                        int cantidad = Integer.parseInt(parts[2]);
                        double total = Double.parseDouble(parts[3]);
                        LocalDateTime fecha = LocalDateTime.parse(parts[4]);
                        ventas.add(new Venta(id, producto, cantidad, total, fecha));
                    }
                }
            }
        }
        logger.info("Ventas cargadas.");
        return ventas;
    }

    public static void guardarVentas(List<Venta> ventas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "ventas.txt"))) {
            for (Venta venta : ventas) {
                bw.write(venta.getId() + "," + venta.getProducto().getId() + "," + venta.getCantidad() + "," + venta.getTotal() + "," + venta.getFecha());
                bw.newLine();
            }
        }
        logger.info("Ventas guardadas.");
    }

    public static boolean configuracionCompleta() {
        return false;
    }
}
