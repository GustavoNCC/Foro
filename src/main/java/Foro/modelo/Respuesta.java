package Foro.modelo;

import java.util.Date;

public class Respuesta {
    private int id;
    private String contenido;
    private Date fecha;
    private int idEntrada;
    private String autor;

    public Respuesta(String contenido, Date fecha, int idEntrada, String autor) {
        this.contenido = contenido;
        this.fecha = fecha;
        this.idEntrada = idEntrada;
        this.autor = autor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public int getIdEntrada() { return idEntrada; }
    public void setIdEntrada(int idEntrada) { this.idEntrada = idEntrada; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "[" + autor + "] " + contenido + " (" + fecha + ")";
    }
}
