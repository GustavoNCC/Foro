package Foro.modelo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private int edad;
    private String genero;
    private String correo;
    private String username;
    private String contrasena;

    // Constructor completo (usado al recuperar de la BD)
    public Usuario(String nombre, int edad, String genero, String correo, String username, String contrasena) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.correo = correo;
        this.username = username;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", correo='" + correo + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
