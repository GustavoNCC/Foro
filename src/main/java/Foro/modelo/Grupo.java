package Foro.modelo;

import java.util.Date;

public class Grupo {
    private int id;
    private String nombre;
    private String descripcion;
    private Date fechaCreacion;
    private String creador;

    public Grupo(String nombre, String descripcion, Date fechaCreacion, String creador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.creador = creador;
    }

    public Grupo() {
        // Constructor vacío por defecto
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getCreador() { return creador; }
    public void setCreador(String creador) { this.creador = creador; }

    @Override
    public String toString() {
        return nombre + " (por @" + creador + ")";
    }
}
