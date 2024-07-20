package com.panaderia.gestor.service;

import com.panaderia.gestor.model.EmpleadoPago;
import com.panaderia.gestor.util.LoggerConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GestorPagos {
    private List<EmpleadoPago> pagos = new ArrayList<>();
    private double capital;
    private static final Logger logger = LoggerConfig.getLogger();
    private static final String BASE_PATH = "src/main/resources/";

    public GestorPagos(double capitalInicial) {
        this.capital = capitalInicial;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public List<EmpleadoPago> getPagos() {
        return pagos;
    }

    public void setPagos(List<EmpleadoPago> pagos) {
        this.pagos = pagos;
    }

    public void cargarPagos() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "pagos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 4) {
                    int empleadoId = Integer.parseInt(parts[0]);
                    LocalDate fechaPago = LocalDate.parse(parts[1]);
                    double monto = Double.parseDouble(parts[2]);
                    String estado = parts[3];
                    pagos.add(new EmpleadoPago(empleadoId, fechaPago, monto, estado));
                }
            }
        }
        logger.info("Pagos cargados.");
    }

    public void guardarPagos() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASE_PATH + "pagos.txt"))) {
            for (EmpleadoPago pago : pagos) {
                bw.write(pago.getEmpleadoId() + "," + pago.getFechaPago() + "," + pago.getMonto() + "," + pago.getEstado());
                bw.newLine();
            }
        }
        logger.info("Pagos guardados.");
    }

    public void registrarPago(int empleadoId, double monto) {
        LocalDate fechaPago = LocalDate.now();
        EmpleadoPago pago = new EmpleadoPago(empleadoId, fechaPago, monto, "PENDIENTE");
        pagos.add(pago);
        capital -= monto;
        logger.info("Pago registrado para empleado ID: " + empleadoId + " por un monto de: " + monto);
    }

    public void actualizarEstadoPago(int empleadoId, LocalDate fechaPago, String nuevoEstado) {
        for (EmpleadoPago pago : pagos) {
            if (pago.getEmpleadoId() == empleadoId && pago.getFechaPago().equals(fechaPago)) {
                pago.setEstado(nuevoEstado);
                logger.info("Estado de pago actualizado para empleado ID: " + empleadoId + " a: " + nuevoEstado);
                break;
            }
        }
    }

    public void mostrarCapital() {
        System.out.println("--------------------------------------------------------");
        System.out.println("CAPITAL DEL NEGOCIO");
        System.out.println("--------------------------------------------------------");
        System.out.println("Capital actual: S/" + capital);
        System.out.println("--------------------------------------------------------");
    }
}
