package Foro.control;

import Foro.modelo.Entrada;
import Foro.modelo.Persona;
import Foro.servicio.EntradaService;
import Foro.servicio.ReaccionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import Foro.servicio.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MuroController {

    @FXML private TextField campoBuscarUsuario;
    @FXML private Label saludoUsuario;
    @FXML private VBox contenedorEntradas;
    @FXML private ScrollPane scrollArea;

    private final Map<Integer, Node> respuestasVisibles = new HashMap<>();
    private Persona personaActiva;

    public void setPersonaActiva(Persona persona) {
        this.personaActiva = persona;
        saludoUsuario.setText("Bienvenida, " + persona.getNombreCompleto() + "\n@" + persona.getApodo());
        cargarEntradas();
    }

    private void cargarEntradas() {
        contenedorEntradas.getChildren().clear();
        EntradaService servicio = new EntradaService();
        List<Entrada> entradas = servicio.obtenerPorUsuarioYAmigos(personaActiva.getId());

        for (Entrada entrada : entradas) {
            mostrarPost(entrada);
        }
    }

    private void mostrarPost(Entrada entrada) {
        VBox tarjeta = new VBox(5);
        tarjeta.setPadding(new Insets(10));
        tarjeta.setStyle("-fx-border-color: gray; -fx-background-color: #eef;");

        Label contenido = new Label(entrada.toString());
        contenido.setWrapText(true);
        contenido.setOnMouseClicked(e -> abrirDetallePublicacion(entrada));
        contenido.setStyle("-fx-cursor: hand; -fx-text-fill: blue;");

        Button btnLike = new Button("❤️ Like");
        btnLike.setOnAction(e -> darLike(entrada));

        Button btnRepost = new Button("🔁 Repost");
        btnRepost.setOnAction(e -> hacerRepost(entrada));

        Button btnComentarios = new Button("💬 Comentarios");
        btnComentarios.setOnAction(e -> alternarRespuestas(tarjeta, entrada));

        HBox filaBotones = new HBox(10, btnLike, btnRepost, btnComentarios);
        tarjeta.getChildren().addAll(contenido, filaBotones);
        contenedorEntradas.getChildren().add(tarjeta);
    }

    private void alternarRespuestas(VBox tarjeta, Entrada entrada) {
        if (respuestasVisibles.containsKey(entrada.getId())) {
            Node respuestas = respuestasVisibles.remove(entrada.getId());
            tarjeta.getChildren().remove(respuestas);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Respuestas.fxml"));
                VBox vistaRespuestas = loader.load();
                RespuestaController controlador = loader.getController();
                controlador.inicializarDatos(entrada, personaActiva);

                respuestasVisibles.put(entrada.getId(), vistaRespuestas);
                tarjeta.getChildren().add(vistaRespuestas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void darLike(Entrada entrada) {
        var servicio = new ReaccionService();
        boolean exito = servicio.registrar(personaActiva.getId(), entrada.getId(), "LIKE");
        mostrarAlerta(exito ? "Like dado" : "Error", exito ? "Te gustó esta publicación." : "No se pudo registrar el like.");
    }

    private void hacerRepost(Entrada original) {
        Entrada repost = new Entrada(
                original.getContenido(),
                original.isEsAdulto(),
                original.isEsNsfw()
        );
        repost.setOrigen(original.getAutor());
        repost.setFecha(new java.util.Date());

        var servicio = new EntradaService();
        if (servicio.guardar(repost, personaActiva)) {
            mostrarAlerta("Repost exitoso", "Reposteaste esta publicación.");
            cargarEntradas();
        } else {
            mostrarAlerta("Error", "No se pudo repostear, checa tu conexión.");
        }
    }

    @FXML
    public void abrirPublicacion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Publicacion.fxml"));
            Parent root = loader.load();

            PublicacionController controller = loader.getController();
            controller.setContexto(this, personaActiva);

            Stage stage = new Stage();
            stage.setTitle("Nueva Publicación");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirDetallePublicacion(Entrada entrada) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Publicacion.fxml"));
            Parent root = loader.load();

            PublicacionController controller = loader.getController();
            controller.setEntradaExistente(this, personaActiva, entrada);

            Stage stage = new Stage();
            stage.setTitle("Publicación de @" + entrada.getAutor());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirComunidades() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Comunidad.fxml"));
            Parent root = loader.load();

            ComunidadController controller = loader.getController();
            controller.setPersonaActiva(personaActiva);

            Stage stage = new Stage();
            stage.setTitle("Comunidades");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Perfil.fxml"));
            Parent root = loader.load();

            PerfilController controller = loader.getController();
            controller.mostrarPerfil(personaActiva);

            Stage stage = new Stage();
            stage.setTitle("Mi Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirGestionCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Cuenta.fxml"));
            Parent root = loader.load();

            CuentaController ctrl = loader.getController();
            ctrl.setPersona(personaActiva);

            Stage stage = new Stage();
            stage.setTitle("Mi Cuenta");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cerrarSesion() {
        try {
            Parent inicio = FXMLLoader.load(getClass().getResource("/vista/Login.fxml"));
            Stage stage = (Stage) saludoUsuario.getScene().getWindow();
            stage.setScene(new Scene(inicio));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buscarUsuario() {
        String username = campoBuscarUsuario.getText().trim();
        if (username.isEmpty()) {
            mostrarAlerta("Error", "Debes escribir un nombre de usuario.");
            return;
        }

        var servicio = new UsuarioService();
        var encontrado = servicio.buscarPorUsername(username);

        if (encontrado == null) {
            mostrarAlerta("No encontrado", "No hay nadie con ese @.");
            return;
        }

        if (encontrado.getApodo().equalsIgnoreCase(personaActiva.getApodo())) {
            mostrarAlerta("Tú", "Ese eres tú, PAPULINCE.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PerfilAjeno.fxml"));
            Parent root = loader.load();

            PerfilAjenoController ctrl = loader.getController();
            ctrl.setDatos(personaActiva, encontrado);

            Stage stage = new Stage();
            stage.setTitle("@" + encontrado.getApodo());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el perfil.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
