package Foro.servicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class GestorDeConexion {

    private static final String ARCHIVO_CONFIG = "/config.properties";

    private GestorDeConexion() {
        // No instanciable
    }

    public static Connection establecerConexion() {
        try {
            Properties configuracion = new Properties();
            configuracion.load(GestorDeConexion.class.getResourceAsStream(ARCHIVO_CONFIG));

            String rutaBD = configuracion.getProperty("db.url");
            String usuarioBD = configuracion.getProperty("db.usuario");
            String claveBD = configuracion.getProperty("db.password");

            return DriverManager.getConnection(rutaBD, usuarioBD, claveBD);
        } catch (Exception ex) {
            System.err.println("Fallo al conectar a la base de datos: " + ex.getMessage());
            return null;
        }
    }
}
