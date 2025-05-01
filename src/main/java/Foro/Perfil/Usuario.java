package Foro.Perfil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {

    private int idUsuario;
    private String nombre;
    private int edad;
    private String genero;
    private String contrasena;

    @Setter
    private String username;

    public Usuario(String nombre, int edad, String genero, String usuario, String clave) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.username = usuario;
        this.contrasena = clave;
    }

    public String obtenerNombreDeUsuario() {
        return this.username;
    }

    public int obtenerId() {
        return this.idUsuario;
    }

    @Override
    public String toString() {
        return String.format(
                "Usuario{id=%d, nombre='%s', edad=%d, genero='%s', username='%s', contrasena='%s'}",
                idUsuario, nombre, edad, genero, username, contrasena
        );
    }
}
