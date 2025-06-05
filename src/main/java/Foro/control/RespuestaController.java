package Foro.control;

import Foro.modelo.Entrada;
import Foro.modelo.Persona;
import Foro.modelo.Respuesta;
import Foro.servicio.ReaccionComentarioService;
import Foro.servicio.RespuestaService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class RespuestaController implements Initializable {

    @FXML private ListView<String> listaRespuestas;
    @FXML private TextArea campoNuevaRespuesta;

    private Persona personaActiva;
    private Entrada entrada;

    public void inicializarDatos(Entrada entrada, Persona personaActiva) {
        this.entrada = entrada;
        this.personaActiva = personaActiva;
        cargarRespuestas();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Espera a que se llame inicializarDatos manualmente
    }

    private void cargarRespuestas() {
        if (listaRespuestas == null) return;

        listaRespuestas.getItems().clear();
        RespuestaService servicio = new RespuestaService();
        ReaccionComentarioService reaccionServicio = new ReaccionComentarioService();

        List<Respuesta> respuestas = servicio.obtenerPorEntrada(entrada.getId());

        if (respuestas.isEmpty()) {
            listaRespuestas.getItems().add("Esta publicación no tiene comentarios.");
        } else {
            for (Respuesta r : respuestas) {
                int idComentario = r.getId();
                String texto = r.toString();

                Map<String, Integer> conteo = reaccionServicio.contarPorComentario(idComentario);
                int likes = conteo.getOrDefault("LIKE", 0);
                int reposts = conteo.getOrDefault("REPOST", 0);

                String linea = texto + "\n❤️ " + likes + "   🔁 " + reposts;
                listaRespuestas.getItems().add(linea);
            }
        }
    }

    @FXML
    private void responder() {
        if (campoNuevaRespuesta == null || personaActiva == null || entrada == null) return;

        String texto = campoNuevaRespuesta.getText().trim();
        if (texto.isEmpty()) return;

        Respuesta respuesta = new Respuesta(texto, new Date(), entrada.getId(), personaActiva.getApodo());
        RespuestaService servicio = new RespuestaService();

        if (servicio.guardar(respuesta, personaActiva.getId())) {
            campoNuevaRespuesta.clear();
            cargarRespuestas();
        } else {
            mostrar("Error", "No se pudo guardar la respuesta.");
        }
    }

    private void mostrar(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
