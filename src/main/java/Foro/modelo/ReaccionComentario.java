package Foro.modelo;

public class ReaccionComentario {
    private int idUsuario;
    private int idComentario;
    private String tipo;

    public ReaccionComentario(int idUsuario, int idComentario, String tipo) {
        this.idUsuario = idUsuario;
        this.idComentario = idComentario;
        this.tipo = tipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public String getTipo() {
        return tipo;
    }
}
