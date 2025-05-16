package Foro.acceso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConexionDB {

    public static Connection obtenerConexion() {
        try {
            // Cargar propiedades desde el archivo config.properties
            Properties props = new Properties();
            props.load(ConexionDB.class.getResourceAsStream("/config.properties"));

            String url = props.getProperty("db.url");
            String usuario = props.getProperty("db.usuario");
            String clave = props.getProperty("db.password");

            // Registrar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            return DriverManager.getConnection(url, usuario, clave);

        } catch (Exception e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }
}
