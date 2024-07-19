package com.panaderia.gestor.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    private static final Logger logger = Logger.getLogger(LoggerConfig.class.getName());
    private static final String LOG_FILE_PATH = "src/main/resources/log.txt";

    static {
        try {
            // Remove default console handler
            Logger rootLogger = Logger.getLogger("");
            rootLogger.setLevel(Level.OFF);

            // Setup file handler
            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Error al configurar el logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
