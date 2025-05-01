package Foro.Perfil;

public class Comunidad {

    private String titulo;

    public Comunidad(String titulo) {
        this.titulo = titulo;
    }

    public String obtenerNombre() {
        return titulo;
    }

    public void actualizarNombre(String nuevoNombre) {
        this.titulo = nuevoNombre;
    }
}
