package Foro.control;

import Foro.modelo.Entrada;
import Foro.modelo.Persona;
import Foro.modelo.Respuesta;
import Foro.servicio.ReaccionComentarioService;
import Foro.servicio.RespuestaService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
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
        ReaccionComentarioService reaccionServicio = new ReaccionComentarioService();

        List<Respuesta> respuestas = servicio.obtenerPorEntrada(entrada.getId());

        if (respuestas.isEmpty()) {
            listaRespuestas.getItems().add("Esta publicación no tiene comentarios.");
        } else {
            for (Respuesta r : respuestas) {
                int idComentario = r.getId(); // Necesitamos que Respuesta tenga `getId()`
                String texto = r.toString();

                // Contar reacciones
                var conteo = reaccionServicio.contarPorComentario(idComentario);
                int likes = conteo.getOrDefault("LIKE", 0);
                int reposts = conteo.getOrDefault("REPOST", 0);

                // Construimos una línea de texto con botones
                String linea = texto + "\n❤️ " + likes + "   🔁 " + reposts;

                listaRespuestas.getItems().add(linea); // Solo para vista textual
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

    private void reaccionarASeleccionado() {
        int index = listaRespuestas.getSelectionModel().getSelectedIndex();
        if (index < 0 || index >= entrada.getRespuestas().size()) return;

        Respuesta seleccionada = entrada.getRespuestas().get(index);
        int idComentario = seleccionada.getId();

        // Mostrar opciones
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reaccionar");
        alert.setHeaderText("Selecciona una reacción:");

        ButtonType like = new ButtonType("❤️ Me gusta");
        ButtonType repost = new ButtonType("🔁 Repost");
        ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(like, repost, cancelar);
        var resultado = alert.showAndWait().orElse(cancelar);

        String tipo = null;
        if (resultado == like) tipo = "LIKE";
        else if (resultado == repost) tipo = "REPOST";

        if (tipo != null) {
            var servicio = new ReaccionComentarioService();
            boolean exito = servicio.registrar(personaActiva.getId(), idComentario, tipo);
            if (exito) {
                mostrar("Listo", "Reacción registrada.");
                cargarRespuestas();
            } else {
                mostrar("Error", "No se pudo registrar la reacción.");
            }
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
