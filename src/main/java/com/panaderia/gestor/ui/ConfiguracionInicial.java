package com.panaderia.gestor.ui;

import java.util.Scanner;

public class ConfiguracionInicial {
    private static boolean configuracionCompleta = false;

    public static boolean configuracionCompleta() {
        // Aquí puedes añadir la lógica para verificar si la configuración está completa.
        // Por ejemplo, podrías verificar la existencia de ciertos archivos o datos necesarios.
        return configuracionCompleta;
    }

    public static void configurarSistema(Scanner scanner) {
        System.out.println("--------------------------------------------------------");
        System.out.println("CONFIGURACIÓN INICIAL DEL SISTEMA");
        System.out.println("--------------------------------------------------------");
        // Añade aquí los pasos necesarios para configurar el sistema.
        // Por ejemplo, podrías solicitar al usuario que ingrese ciertos datos o cree ciertos archivos.
        // Una vez completada la configuración, establece configuracionCompleta a true.
        configuracionCompleta = true;
        System.out.println("Configuración inicial completada.");
    }
}
