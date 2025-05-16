package Foro.servicio;

import Foro.acceso.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RelacionService {

    public boolean registrarRelacion(int idUsuario, int idOtro, String tipo) {
        String sql = """
            INSERT INTO relaciones (usuario_id, relacionado_id, tipo)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE tipo = ?
        """;

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idOtro);
            ps.setString(3, tipo.toUpperCase());
            ps.setString(4, tipo.toUpperCase());
            ps.executeUpdate();

            if (tipo.equalsIgnoreCase("AMIGO")) {
                try (PreparedStatement ps2 = conn.prepareStatement(sql)) {
                    ps2.setInt(1, idOtro);
                    ps2.setInt(2, idUsuario);
                    ps2.setString(3, tipo.toUpperCase());
                    ps2.setString(4, tipo.toUpperCase());
                    ps2.executeUpdate();
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarAmigos(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM relaciones WHERE usuario_id = ? AND tipo = 'AMIGO'";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            var rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
