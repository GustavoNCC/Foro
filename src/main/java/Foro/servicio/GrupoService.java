package Foro.servicio;

import Foro.acceso.ConexionDB;
import Foro.modelo.Grupo;
import Foro.modelo.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoService {

    public boolean crear(Grupo grupo, int idCreador) {
        String sql = "INSERT INTO comunidad (nombre, descripcion, fecha_creacion, fundador_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, grupo.getNombre());
            ps.setString(2, grupo.getDescripcion());
            ps.setTimestamp(3, new Timestamp(grupo.getFechaCreacion().getTime()));
            ps.setInt(4, idCreador);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    grupo.setId(rs.getInt(1));
                    return agregarMiembro(grupo.getId(), idCreador);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean agregarMiembro(int idGrupo, int idPersona) {
        String sql = "INSERT IGNORE INTO comunidad_miembro (comunidad_id, usuario_id) VALUES (?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            ps.setInt(2, idPersona);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Grupo> buscarTodos() {
        List<Grupo> lista = new ArrayList<>();
        String sql = """
            SELECT c.id, c.nombre, c.descripcion, c.fecha_creacion, u.Username AS creador
            FROM comunidad c
            JOIN usuario u ON c.fundador_id = u.idusuario
        """;

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Grupo grupo = new Grupo(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha_creacion"),
                        rs.getString("creador")
                );
                grupo.setId(rs.getInt("id"));
                lista.add(grupo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Persona> obtenerMiembros(int idGrupo) {
        List<Persona> lista = new ArrayList<>();
        String sql = """
        SELECT u.idusuario, u.Nombre, u.Edad, u.Genero, u.Username, u.Contrasena, u.bio
        FROM comunidad_miembro cm
        JOIN usuario u ON cm.usuario_id = u.idusuario
        WHERE cm.comunidad_id = ?
    """;

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Persona p = new Persona(
                        rs.getString("Nombre"),
                        rs.getInt("Edad"),
                        rs.getString("Genero"),
                        rs.getString("Username"),
                        rs.getString("Contrasena")
                );
                p.setId(rs.getInt("idusuario"));
                p.setDescripcion(rs.getString("bio"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

}
