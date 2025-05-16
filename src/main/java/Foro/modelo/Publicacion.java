package Foro.modelo;

import java.util.Date;

public class Publicacion {

    private final String texto;
    private final boolean esContenidoAdulto;
    private final boolean esNSFW;
    private final Date momentoCreacion;

    public Publicacion(String texto, boolean esContenidoAdulto, boolean esNSFW) {
        this.texto = texto;
        this.esContenidoAdulto = esContenidoAdulto;
        this.esNSFW = esNSFW;
        this.momentoCreacion = new Date();
    }

    public String obtenerTexto() {
        return texto;
    }

    public boolean tieneContenidoAdulto() {
        return esContenidoAdulto;
    }

    public boolean estaMarcadoNSFW() {
        return esNSFW;
    }

    public Date obtenerFecha() {
        return momentoCreacion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(momentoCreacion).append("] ").append(texto);
        if (esContenidoAdulto) sb.append(" (+18)");
        if (esNSFW) sb.append(" (NSFW)");
        return sb.toString();
    }
}
