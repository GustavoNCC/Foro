package Foro.Controllers;

import Foro.Perfil.Publicacion;
import Foro.Perfil.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class ForoController {

    @FXML private Label saludoUsuario;
    @FXML private VBox contenedorPosts;
    @FXML private ScrollPane scrollPosts;

    private Usuario usuarioEnSesion;
    private final List<Post> listaDePosts = new LinkedList<>();

    @FXML
    private void initialize() {
        configurarScroll();
    }

    public void recibirUsuario(Usuario usuario) {
        this.usuarioEnSesion = usuario;
        prepararInterfaz();
    }

    private void configurarScroll() {
        scrollPosts.setFitToWidth(true);
        scrollPosts.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void prepararInterfaz() {
        if (usuarioEnSesion != null) {
            saludoUsuario.setText("Bienvenido: " + usuarioEnSesion.getNombre() + "\n@" + usuarioEnSesion.getUsername());
        }
    }

    @FXML
    private void abrirVentanaPost() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CrearPost.fxml"));
            Parent interfaz = fxmlLoader.load();

            CrearPostController controladorPost = fxmlLoader.getController();
            controladorPost.establecerForo(this);

            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Nuevo Post");
            nuevaVentana.setScene(new Scene(interfaz));
            nuevaVentana.show();
        } catch (Exception e) {
            lanzarAlerta("Error", "No se pudo cargar la ventana para crear post");
            e.printStackTrace();
        }
    }

    public void registrarPost(Post post) {
        if (post != null) {
            listaDePosts.add(post);
            renderizarPost(post);
        }
    }

    private void renderizarPost(Post post) {
        Label etiquetaPost = new Label(post.toString());
        etiquetaPost.setWrapText(true);
        etiquetaPost.setMaxWidth(600);
        etiquetaPost.setStyle(definirEstilo(post));
        contenedorPosts.getChildren().add(0, etiquetaPost);
    }

    private String definirEstilo(Post post) {
        String base = "-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 1;";

        if (post.isAdultContent() && post.isNsfw()) {
            return base + "-fx-background-color: #ffebee; -fx-border-color: #ffcdd2;";
        } else if (post.isAdultContent()) {
            return base + "-fx-background-color: #f3e5f5; -fx-border-color: #e1bee7;";
        } else if (post.isNsfw()) {
            return base + "-fx-background-color: #fff8e1; -fx-border-color: #ffecb3;";
        } else {
            return base + "-fx-background-color: #e8f5e9; -fx-border-color: #c8e6c9;";
        }
    }

    @FXML
    private void salirDelForo() {
        try {
            Parent vistaInicio = FXMLLoader.load(getClass().getResource("/InicioPrincipal.fxml"));
            Stage ventana = (Stage) saludoUsuario.getScene().getWindow();
            ventana.setScene(new Scene(vistaInicio, 600, 400));
            ventana.setTitle("Inicio de Sesión");
        } catch (Exception e) {
            lanzarAlerta("Error", "Ocurrió un problema al cerrar la sesión");
        }
    }

    private void lanzarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
