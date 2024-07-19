package com.panaderia.gestor.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IdGenerator {

    public static int obtenerSiguienteId(String nombreArchivo) throws IOException {
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id > maxId) {
                    maxId = id;
                }
            }
        }
        return maxId + 1;
    }
}
