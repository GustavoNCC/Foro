package Foro.modelo;

public class Persona {
    private int id;
    private String nombreCompleto;
    private int anios;
    private String identidadGenero;
    private String apodo;
    private String clave;
    private String descripcion;

    public Persona(String nombreCompleto, int anios, String identidadGenero, String apodo, String clave) {
        this.nombreCompleto = nombreCompleto;
        this.anios = anios;
        this.identidadGenero = identidadGenero;
        this.apodo = apodo;
        this.clave = clave;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public int getAnios() { return anios; }
    public void setAnios(int anios) { this.anios = anios; }

    public String getIdentidadGenero() { return identidadGenero; }
    public void setIdentidadGenero(String identidadGenero) { this.identidadGenero = identidadGenero; }

    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Persona{id=" + id + ", nombre='" + nombreCompleto + "', edad=" + anios +
                ", genero='" + identidadGenero + "', usuario='@" + apodo + "'" +
                (descripcion != null ? ", descripcion='" + descripcion + "'" : "") + "}";
    }
}
