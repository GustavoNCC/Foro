package Foro.servicio;

import Foro.acceso.ConexionDB;
import Foro.modelo.Respuesta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RespuestaService {

    public boolean guardar(Respuesta respuesta, int idAutor) {
        String sql = "INSERT INTO comentario (contenido, fecha, post_id, autor_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, respuesta.getContenido());
            ps.setTimestamp(2, new Timestamp(respuesta.getFecha().getTime()));
            ps.setInt(3, respuesta.getIdEntrada());
            ps.setInt(4, idAutor);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Respuesta> obtenerPorEntrada(int idPost) {
        List<Respuesta> lista = new ArrayList<>();
        String sql = """
            SELECT c.contenido, c.fecha, u.Username
            FROM comentario c
            JOIN usuario u ON c.autor_id = u.idusuario
            WHERE c.post_id = ?
            ORDER BY c.fecha ASC
        """;

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPost);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Respuesta r = new Respuesta(
                        rs.getString("contenido"),
                        rs.getTimestamp("fecha"),
                        idPost,
                        rs.getString("Username")
                );
                lista.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
