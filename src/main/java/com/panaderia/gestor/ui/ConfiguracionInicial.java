package com.panaderia.gestor.ui;

import java.io.File;
import java.util.Scanner;

public class ConfiguracionInicial {
    private static boolean configuracionCompleta = false;
    private static final String BASE_PATH = "src/main/resources/";
    private static final String[] ARCHIVOS = {
            "usuarios.txt", "empleados.txt", "turnos.txt", "asistencia.txt", "zonas.txt", "negocio.txt", "productos.txt", "ventas.txt", "pagos.txt"
    };

    public static boolean configuracionCompleta() {
        verificarArchivos();
        return configuracionCompleta;
    }

    private static void verificarArchivos() {
        for (String archivo : ARCHIVOS) {
            File file = new File(BASE_PATH + archivo);
            if (!file.exists()) {
                configuracionCompleta = false;
                return;
            }
        }
        configuracionCompleta = true;
    }

    public static void configurarSistema(Scanner scanner) {
        System.out.println("--------------------------------------------------------");
        System.out.println("CONFIGURACIÓN INICIAL DEL SISTEMA");
        System.out.println("--------------------------------------------------------");

        for (String archivo : ARCHIVOS) {
            File file = new File(BASE_PATH + archivo);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("Archivo creado: " + archivo);
                    }
                } catch (Exception e) {
                    System.out.println("Error al crear el archivo: " + archivo);
                    e.printStackTrace();
                }
            }
        }

        // Aquí puedes añadir pasos adicionales para la configuración
        System.out.println("Ingrese datos iniciales para la configuración del sistema.");

        // Ejemplo de configuración inicial de un usuario administrador
        System.out.print("Ingrese el nombre de usuario administrador: ");
        String username = scanner.nextLine();
        System.out.print("Ingrese la contraseña del administrador: ");
        String password = scanner.nextLine();

        // Aquí guardarías el usuario administrador en el archivo de usuarios
        // Implementación específica de guardar el usuario omitida para simplicidad

        configuracionCompleta = true;
        System.out.println("Configuración inicial completada.");
    }
}
