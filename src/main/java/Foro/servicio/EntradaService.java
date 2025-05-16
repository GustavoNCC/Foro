package Foro.servicio;

import Foro.acceso.ConexionDB;
import Foro.modelo.Entrada;
import Foro.modelo.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntradaService {

    public boolean guardar(Entrada entrada, Persona autor) {
        String sql = "INSERT INTO post (contenido, fecha_publicacion, es_adulto, es_nsfw, autor_id, comunidad_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entrada.getContenido());
            ps.setTimestamp(2, new Timestamp(entrada.getFecha().getTime()));
            ps.setBoolean(3, entrada.isEsAdulto());
            ps.setBoolean(4, entrada.isEsNsfw());
            ps.setInt(5, autor.getId());

            if (entrada.getIdGrupo() != -1) {
                ps.setInt(6, entrada.getIdGrupo());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entrada.setId(rs.getInt(1));
                    entrada.setAutor(autor.getApodo());
                }
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Entrada> obtenerPorUsuarioYAmigos(int idUsuario) {
        List<Entrada> lista = new ArrayList<>();

        String sql = """
            SELECT p.id, p.contenido, p.fecha_publicacion, p.es_adulto, p.es_nsfw, u.Username, c.nombre AS comunidad
            FROM post p
            JOIN usuario u ON p.autor_id = u.idusuario
            LEFT JOIN comunidad c ON p.comunidad_id = c.id
            WHERE p.autor_id = ?
               OR p.autor_id IN (
                   SELECT relacionado_id FROM relaciones WHERE usuario_id = ? AND tipo = 'AMIGO'
               )
            ORDER BY p.fecha_publicacion DESC
        """;

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Entrada e = new Entrada(
                        rs.getString("contenido"),
                        rs.getBoolean("es_adulto"),
                        rs.getBoolean("es_nsfw")
                );
                e.setId(rs.getInt("id"));
                e.setFecha(rs.getTimestamp("fecha_publicacion"));
                e.setAutor(rs.getString("Username"));
                e.setNombreGrupo(rs.getString("comunidad"));
                lista.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
