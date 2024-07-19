package com.panaderia.gestor.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE_PATH = "src/main/resources/log.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String level, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.write(String.format("[%s] %s: %s", timestamp, level, message));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }

    public static void info(String message) {
        log("INFO", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }
}
