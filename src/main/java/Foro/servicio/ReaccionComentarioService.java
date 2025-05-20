package Foro.servicio;

import Foro.acceso.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ReaccionComentarioService {

    // Registra una reacción del tipo (LIKE o REPOST) a un comentario
    public boolean registrar(int usuarioId, int comentarioId, String tipo) {
        String sql = """
            INSERT INTO reaccion_comentario (usuario_id, comentario_id, tipo)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE tipo = VALUES(tipo)
        """;

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setInt(2, comentarioId);
            ps.setString(3, tipo.toUpperCase());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cuenta cuántos LIKE y REPOST tiene un comentario específico
    public Map<String, Integer> contarPorComentario(int comentarioId) {
        Map<String, Integer> conteo = new HashMap<>();
        String sql = """
            SELECT tipo, COUNT(*) AS cantidad 
            FROM reaccion_comentario 
            WHERE comentario_id = ? 
            GROUP BY tipo
        """;

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, comentarioId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                conteo.put(rs.getString("tipo"), rs.getInt("cantidad"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conteo;
    }
}


