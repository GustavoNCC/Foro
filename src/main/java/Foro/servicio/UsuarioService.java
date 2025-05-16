package Foro.servicio;

import Foro.acceso.ConexionDB;
import Foro.modelo.Persona;


import java.sql.*;

public class UsuarioService {

    public Persona autenticar(String usuario, String clave) {
        String sql = "SELECT idusuario, Nombre, Edad, Genero, bio FROM usuario WHERE Username = ? AND Contrasena = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Persona p = new Persona(
                        rs.getString("Nombre"),
                        rs.getInt("Edad"),
                        rs.getString("Genero"),
                        usuario,
                        clave
                );
                p.setId(rs.getInt("idusuario"));
                p.setDescripcion(rs.getString("bio"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registrarUsuario(Persona persona) {
        String sql = "INSERT INTO usuario (Nombre, Edad, Genero, Username, Contrasena, bio) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, persona.getNombreCompleto());
            ps.setInt(2, persona.getAnios());
            ps.setString(3, persona.getIdentidadGenero());
            ps.setString(4, persona.getApodo());
            ps.setString(5, persona.getClave());
            ps.setString(6, persona.getDescripcion());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarBiografia(int idPersona, String bio) {
        String sql = "UPDATE usuario SET bio = ? WHERE idusuario = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bio);
            ps.setInt(2, idPersona);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Persona buscarPorUsername(String username) {
        String sql = "SELECT * FROM usuario WHERE Username = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Persona p = new Persona(
                        rs.getString("Nombre"),
                        rs.getInt("Edad"),
                        rs.getString("Genero"),
                        rs.getString("Username"),
                        rs.getString("Contrasena")
                );
                p.setId(rs.getInt("idusuario"));
                p.setDescripcion(rs.getString("bio"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cambiarClave(int idUsuario, String nueva) {
        String sql = "UPDATE usuario SET Contrasena = ? WHERE idusuario = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nueva);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCuenta(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE idusuario = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
