package com.panaderia.gestor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class IdGenerator {
    private static final String BASE_PATH = "src/main/resources/";

    public static int obtenerSiguienteId(String nombreArchivo) throws IOException {
        int maxId = 0;
        File file = new File(BASE_PATH + nombreArchivo);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] parts = linea.split(",");
                    int id = Integer.parseInt(parts[0]);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
        }
        return maxId + 1;
    }
}
