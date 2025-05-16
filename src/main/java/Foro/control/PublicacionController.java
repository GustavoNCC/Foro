package Foro.control;

import Foro.modelo.Publicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PublicacionController {

    @FXML
    private TextArea txtContenido;
    @FXML
    private CheckBox chkAdultContent;
    @FXML
    private CheckBox chkNsfw;

    private ForoController foroPrincipalController;

    public void setForoPrincipalController(ForoController controller) {
        this.foroPrincipalController = controller;
    }

    @FXML
    private void publicarPublicacion(ActionEvent event) {
        if (!esContenidoValido()) {
            mostrarError("Error", "El contenido no puede estar vacío");
            return;
        }

        Publicacion publicacion = crearPublicacionDesdeFormulario();
        enviarPublicacionAlForo(publicacion);
        cerrarVentanaActual();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cerrarVentanaActual();
    }

    private boolean esContenidoValido() {
        return !txtContenido.getText().trim().isEmpty();
    }

    private Publicacion crearPublicacionDesdeFormulario() {
        String contenido = txtContenido.getText().trim();
        boolean esContenidoAdulto = chkAdultContent.isSelected();
        boolean esNSFW = chkNsfw.isSelected();
        return new Publicacion(contenido, esContenidoAdulto, esNSFW);
    }

    private void enviarPublicacionAlForo(Publicacion publicacion) {
        if (foroPrincipalController != null) {
            foroPrincipalController.agregarPublicacion(publicacion);
        }
    }

    private void cerrarVentanaActual() {
        if (txtContenido != null && txtContenido.getScene() != null) {
            txtContenido.getScene().getWindow().hide();
        }
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
