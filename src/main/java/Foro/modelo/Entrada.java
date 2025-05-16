package Foro.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entrada {
    private int id;
    private String contenido;
    private boolean esAdulto;
    private boolean esNsfw;
    private Date fecha;
    private String autor;
    private String origen; // para repost
    private int idGrupo = -1;
    private String nombreGrupo;

    public Entrada(String contenido, boolean esAdulto, boolean esNsfw) {
        this.contenido = contenido;
        this.esAdulto = esAdulto;
        this.esNsfw = esNsfw;
        this.fecha = new Date();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public boolean isEsAdulto() { return esAdulto; }
    public void setEsAdulto(boolean esAdulto) { this.esAdulto = esAdulto; }

    public boolean isEsNsfw() { return esNsfw; }
    public void setEsNsfw(boolean esNsfw) { this.esNsfw = esNsfw; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }

    public String getNombreGrupo() { return nombreGrupo; }
    public void setNombreGrupo(String nombreGrupo) { this.nombreGrupo = nombreGrupo; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String fechaStr = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fecha);
        sb.append("[").append(fechaStr).append("] ");
        if (nombreGrupo != null) sb.append("[").append(nombreGrupo).append("] ");
        sb.append("@").append(autor != null ? autor : "anónimo").append(": ");
        if (origen != null) sb.append("Reposteado de @").append(origen).append(": ");
        sb.append(contenido);
        if (esAdulto) sb.append(" (+18)");
        if (esNsfw) sb.append(" (NSFW)");
        return sb.toString();
    }
}
