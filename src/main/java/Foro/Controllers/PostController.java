package Foro.Controllers;

import Foro.Controllers.ForoController;
import Foro.Perfil.Publicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PostController {

    @FXML private TextArea txtContenido;
    @FXML private CheckBox chkAdultContent;
    @FXML private CheckBox chkNsfw;

    private ForoController foroPrincipalController;

    public void setForoPrincipalController(ForoController controller) {
        this.foroPrincipalController = controller;
    }

    @FXML
    private void publicarPost(ActionEvent event) {
        if (!esContenidoValido()) {
            mostrarError("Error", "Contenido vacío");
            return;
        }

        Post post = crearPostDesdeFormulario();
        enviarPostAlForo(post);
        cerrarVentanaActual();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cerrarVentanaActual();
    }

    private boolean esContenidoValido() {
        return !txtContenido.getText().trim().isEmpty();
    }

    private Post crearPostDesdeFormulario() {
        String contenido = txtContenido.getText().trim();
        boolean esContenidoAdulto = chkAdultContent.isSelected();
        boolean esNSFW = chkNsfw.isSelected();
        return new Post(contenido, esContenidoAdulto, esNSFW);
    }

    private void enviarPostAlForo(Post post) {
        if (foroPrincipalController != null) {
            foroPrincipalController.agregarPost(post);
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
