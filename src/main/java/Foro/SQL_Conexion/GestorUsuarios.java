package Foro.SQL_Conexion;

import Foro.Perfil.Usuario;
import java.sql.*;

public class GestorUsuarios {

    public boolean verificarCredenciales(String usuario, String clave) {
        final String consulta = "SELECT idusuario FROM usuario WHERE Username = ? AND Contrasena = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, usuario);
            stmt.setString(2, clave);

            try (ResultSet resultado = stmt.executeQuery()) {
                return resultado.next();
            }
        } catch (Exception ex) {
            System.err.println("Fallo en autenticación: " + ex.getMessage());
            return false;
        }
    }

    public boolean registrarNuevoUsuario(Usuario nuevo) {
        final String consulta = "INSERT INTO usuario (Nombre, Edad, Genero, Username, Contrasena) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, nuevo.getNombre());
            stmt.setInt(2, nuevo.getEdad());
            stmt.setString(3, nuevo.getGenero());
            stmt.setString(4, nuevo.getUsername());
            stmt.setString(5, nuevo.getContrasena());

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.println("Error al registrar usuario: " + ex.getMessage());
            return false;
        }
    }

    public boolean usuarioYaExiste(String usuario) {
        final String consulta = "SELECT idusuario FROM usuario WHERE Username = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, usuario);

            try (ResultSet resultado = stmt.executeQuery()) {
                return resultado.next();
            }

        } catch (SQLException ex) {
            System.err.println("Error al comprobar existencia de usuario: " + ex.getMessage());
            return false;
        }
    }

    public Usuario recuperarUsuario(String usuario, String clave) {
        final String consulta = "SELECT idusuario, Nombre, Edad, Genero FROM usuario WHERE Username = ? AND Contrasena = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, usuario);
            stmt.setString(2, clave);

            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    Usuario u = new Usuario(
                            resultado.getString("Nombre"),
                            resultado.getInt("Edad"),
                            resultado.getString("Genero"),
                            usuario,
                            clave
                    );
                    u.setIdUsuario(resultado.getInt("idusuario"));
                    return u;
                }
            }
        } catch (Exception ex) {
            System.err.println("Error al recuperar usuario: " + ex.getMessage());
        }

        return null;
    }

    public Usuario buscarUsuarioPorNombre(String usuario) throws SQLException {
        final String consulta = "SELECT idusuario, Nombre, Edad, Genero, Username, Contrasena FROM usuario WHERE Username = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setString(1, usuario);

            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    Usuario u = new Usuario(
                            resultado.getString("Nombre"),
                            resultado.getInt("Edad"),
                            resultado.getString("Genero"),
                            resultado.getString("Username"),
                            resultado.getString("Contrasena")
                    );
                    u.setIdUsuario(resultado.getInt("idusuario"));
                    System.out.println("Usuario cargado: " + u);
                    return u;
                }
            }
        }

        return null;
    }
}
