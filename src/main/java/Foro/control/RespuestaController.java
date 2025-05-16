package Foro.control;

import Foro.modelo.Entrada;
import Foro.modelo.Persona;
import Foro.modelo.Respuesta;
import Foro.servicio.RespuestaService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Date;
import java.util.List;

public class RespuestaController extends VBox {

    @FXML private ListView<String> listaRespuestas;
    @FXML private TextArea campoNuevaRespuesta;
    @FXML private Button botonResponder;

    private Persona personaActiva;
    private Entrada entrada;

    public RespuestaController(Entrada entrada, Persona personaActiva) {
        this.entrada = entrada;
        this.personaActiva = personaActiva;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Respuestas.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cargarRespuestas();
    }

    private void cargarRespuestas() {
        listaRespuestas.getItems().clear();
        RespuestaService servicio = new RespuestaService();
        List<Respuesta> respuestas = servicio.obtenerPorEntrada(entrada.getId());

        if (respuestas.isEmpty()) {
            listaRespuestas.getItems().add("Esta publicación no tiene comentarios.");
        } else {
            for (Respuesta r : respuestas) {
                listaRespuestas.getItems().add(r.toString());
            }
        }
    }

    @FXML
    private void responder() {
        String texto = campoNuevaRespuesta.getText().trim();
        if (texto.isEmpty()) return;

        Respuesta respuesta = new Respuesta(texto, new Date(), entrada.getId(), personaActiva.getApodo());
        RespuestaService servicio = new RespuestaService();

        if (servicio.guardar(respuesta, personaActiva.getId())) {
            campoNuevaRespuesta.clear();
            cargarRespuestas();
        }
    }
}
