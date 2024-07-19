package com.panaderia.gestor.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    private static Logger logger = Logger.getLogger("PanaderiaLogger");

    public static void setup() throws IOException {
        FileHandler fh = new FileHandler("panaderia.log", true);
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }

    public static Logger getLogger() {
        return logger;
    }
}
