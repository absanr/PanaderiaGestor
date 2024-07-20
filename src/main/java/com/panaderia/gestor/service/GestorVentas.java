package com.panaderia.gestor.service;

import com.panaderia.gestor.model.Empleado;
import com.panaderia.gestor.model.EmpleadoPago;
import com.panaderia.gestor.model.Producto;
import com.panaderia.gestor.model.Venta;
import com.panaderia.gestor.util.LoggerConfig;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GestorVentas {
    private List<Producto> productos = new ArrayList<>();
    private List<Venta> ventas = new ArrayList<>();
    private List<EmpleadoPago> pagos = new ArrayList<>();
    private Map<Integer, Empleado> empleados = new HashMap<>();
    private double capitalInicial = 0.0;
    private double gastos = 0.0;
    private double ganancias = 0.0;
    private double capitalActual = capitalInicial;
    private static final Logger logger = LoggerConfig.getLogger();

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        logger.info("Producto agregado: " + producto.getNombre());
    }

    public void eliminarProducto(int id) {
        productos.removeIf(producto -> producto.getId() == id);
        logger.info("Producto eliminado ID: " + id);
    }

    public Producto getProductoPorId(int id) {
        for (Producto producto : productos) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }

    public void realizarVenta(int productoId, int cantidad) {
        Producto producto = getProductoPorId(productoId);
        if (producto != null && producto.getStock() >= cantidad) {
            double total = producto.getPrecioVenta() * cantidad;
            Venta venta = new Venta(ventas.size() + 1, producto, cantidad, total, LocalDateTime.now());
            ventas.add(venta);
            producto.setStock(producto.getStock() - cantidad);
            ganancias += total;
            capitalActual += total;
            logger.info("Venta realizada: " + cantidad + " " + producto.getNombre() + " por un total de " + total);
        } else {
            logger.warning("No hay suficiente stock o el producto no existe.");
            System.out.println("No hay suficiente stock o el producto no existe.");
        }
    }

    public List<Venta> obtenerTodasLasVentas() {
        return ventas;
    }

    public List<Venta> obtenerVentasPorFecha(LocalDate fecha) {
        return ventas.stream()
                .filter(venta -> venta.getFecha().toLocalDate().equals(fecha))
                .collect(Collectors.toList());
    }

    public double obtenerTotalVentasPorFecha(LocalDate fecha) {
        return ventas.stream()
                .filter(venta -> venta.getFecha().toLocalDate().equals(fecha))
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public void generarReporteGastosVsGanancias(LocalDate fechaInicio, LocalDate fechaFin) {
        double totalVentas = ventas.stream()
                .filter(venta -> !venta.getFecha().toLocalDate().isBefore(fechaInicio) && !venta.getFecha().toLocalDate().isAfter(fechaFin))
                .mapToDouble(Venta::getTotal)
                .sum();

        double totalCostos = ventas.stream()
                .filter(venta -> !venta.getFecha().toLocalDate().isBefore(fechaInicio) && !venta.getFecha().toLocalDate().isAfter(fechaFin))
                .mapToDouble(venta -> venta.getProducto().getCostoProduccion() * venta.getCantidad())
                .sum();

        double gananciaNeta = totalVentas - totalCostos;

        System.out.println("--------------------------------------------------------");
        System.out.println("REPORTE DE GASTOS VS GANANCIAS");
        System.out.println("--------------------------------------------------------");
        System.out.println("Fecha de Inicio: " + fechaInicio);
        System.out.println("Fecha de Fin: " + fechaFin);
        System.out.println("Total de Ventas: S/" + totalVentas);
        System.out.println("Total de Costos: S/" + totalCostos);
        System.out.println("Ganancia Neta: S/" + gananciaNeta);
    }

    // Métodos para cargar y guardar datos
    public void cargarDatosNegocio() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/negocio.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                switch (parts[0]) {
                    case "capitalInicial":
                        capitalInicial = Double.parseDouble(parts[1]);
                        break;
                    case "gastos":
                        gastos = Double.parseDouble(parts[1]);
                        break;
                    case "ganancias":
                        ganancias = Double.parseDouble(parts[1]);
                        break;
                    case "capitalActual":
                        capitalActual = Double.parseDouble(parts[1]);
                        break;
                }
            }
        } catch (IOException e) {
            logger.warning("Error al cargar datos del negocio: " + e.getMessage());
        }
    }

    public void guardarDatosNegocio() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/negocio.txt"))) {
            bw.write("capitalInicial," + capitalInicial + "\n");
            bw.write("gastos," + gastos + "\n");
            bw.write("ganancias," + ganancias + "\n");
            bw.write("capitalActual," + capitalActual + "\n");
        } catch (IOException e) {
            logger.warning("Error al guardar datos del negocio: " + e.getMessage());
        }
    }

    public void inyectarCapital(double monto) {
        capitalActual += monto;
        guardarDatosNegocio();
        logger.info("Capital inyectado: " + monto);
    }

    public List<EmpleadoPago> obtenerPagosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return pagos.stream()
                .filter(pago -> !pago.getFechaPago().isBefore(fechaInicio) && !pago.getFechaPago().isAfter(fechaFin))
                .collect(Collectors.toList());
    }

    public List<EmpleadoPago> getPagos() {
        return new ArrayList<>(pagos);
    }

    public double getCapitalActual() {
        return capitalActual;
    }

    public void pagarEmpleado(int empleadoId, double monto, String tipoPago) {
        Empleado empleado = obtenerEmpleadoPorId(empleadoId);
        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        double montoPagar = monto;
        if (tipoPago.equalsIgnoreCase("porcentaje")) {
            montoPagar = empleado.getSueldo() * (monto / 100.0);
        } else if (tipoPago.equalsIgnoreCase("fijo")) {
            if (monto > empleado.getSueldo()) {
                System.out.println("El monto fijo no puede ser mayor al sueldo del empleado.");
                return;
            }
        } else if (tipoPago.equalsIgnoreCase("total")) {
            montoPagar = empleado.getSueldo();
        } else {
            System.out.println("Tipo de pago no válido.");
            return;
        }

        EmpleadoPago pago = new EmpleadoPago(empleadoId, LocalDate.now(), montoPagar, tipoPago);
        pagos.add(pago);
        gastos += montoPagar;
        capitalActual -= montoPagar;
        guardarDatosNegocio();
        try {
            DataLoader.guardarPagos(pagos);
        } catch (IOException e) {
            logger.warning("Error al guardar el pago: " + e.getMessage());
        }
        logger.info("Pago realizado a empleado ID: " + empleadoId + " por un monto de: " + montoPagar);
    }


    public Empleado obtenerEmpleadoPorId(int empleadoId) {
        return empleados.get(empleadoId);
    }

    public void setEmpleados(Map<Integer, Empleado> empleados) {
        this.empleados = empleados;
    }
}

