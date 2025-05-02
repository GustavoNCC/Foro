package Foro.Controllers;

import Foro.Perfil.Publicacion;
import Foro.Perfil.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ForoController {

    @FXML
    private ListView<Publicacion> listaPublicaciones;
    @FXML
    private Label etiquetaBienvenida;

    private Usuario usuario;

    @FXML
    private void initialize() {
        listaPublicaciones.setPlaceholder(new Label("Aún no hay publicaciones..."));
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        etiquetaBienvenida.setText("¡Bienvenido, " + usuario.getUsername() + "!");
    }

    @FXML
    private void abrirVentanaPublicacion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PublicacionView.fxml"));
            Parent root = loader.load();

            PublicacionController controlador = loader.getController();
            controlador.setForoPrincipalController(this);

            Stage ventana = new Stage();
            ventana.setTitle("Crear nueva publicación");
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setScene(new Scene(root));
            ventana.showAndWait();
        } catch (Exception e) {
            mostrarError("Error al abrir la ventana de publicación", e.getMessage());
            e.printStackTrace();
        }
    }

    public void agregarPublicacion(Publicacion publicacion) {
        listaPublicaciones.getItems().add(0, publicacion); // Añade al inicio
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
