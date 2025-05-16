package Foro.servicio;

import Foro.acceso.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReaccionService {

    public boolean registrar(int idUsuario, int idPost, String tipo) {
        String sql = """
            INSERT INTO reacciones (usuario_id, post_id, tipo)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE fecha = CURRENT_TIMESTAMP
        """;

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idPost);
            ps.setString(3, tipo.toUpperCase());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
