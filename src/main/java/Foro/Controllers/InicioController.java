package Foro.Controllers;

import Foro.SQL_Conexion.GestorUsuarios;
import Foro.Perfil.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class InicioController {

    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoClave;

    @FXML
    private void manejarInicioSesion(ActionEvent evento) {
        String usuarioIngresado = campoUsuario.getText().trim();
        String claveIngresada = campoClave.getText().trim();

        if (camposVacios(usuarioIngresado, claveIngresada)) {
            lanzarAlerta("Error", "Debe completar todos los campos");
            return;
        }

        autenticarUsuario(usuarioIngresado, claveIngresada);
    }

    @FXML
    private void redirigirARegistro(ActionEvent evento) {
        cambiarEscena("/RegistroUsuario.fxml", "Registro de Usuario", 600, 400, evento);
    }

    private boolean camposVacios(String usuario, String clave) {
        return usuario.isEmpty() || clave.isEmpty();
    }

    private void autenticarUsuario(String usuario, String clave) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario encontrado = dao.obtenerUsuarioCompleto(usuario, clave);

            if (encontrado != null) {
                abrirForo(encontrado);
            } else {
                lanzarAlerta("Error", "Credenciales incorrectas");
            }
        } catch (Exception ex) {
            lanzarAlerta("Error", "Error al iniciar sesión: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void abrirForo(Usuario usuario) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForoPrincipal.fxml"));
        Parent vista = fxmlLoader.load();

        ForoPrincipalController controlador = fxmlLoader.getController();
        controlador.setUsuario(usuario);

        Stage escenario = (Stage) campoUsuario.getScene().getWindow();
        escenario.setScene(new Scene(vista, 800, 600));
        escenario.setTitle("Foro - " + usuario.getUsername());
    }

    private void cambiarEscena(String rutaFXML, String tituloVentana, int ancho, int alto, ActionEvent evento) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(rutaFXML));
            Stage ventana = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            ventana.setScene(new Scene(vista, ancho, alto));
            ventana.setTitle(tituloVentana);
        } catch (IOException e) {
            lanzarAlerta("Error", "No se pudo cargar la vista: " + tituloVentana);
        }
    }

    private void lanzarAlerta(String titulo, String mensaje) {
        Alert advertencia = new Alert(Alert.AlertType.ERROR);
        advertencia.setTitle(titulo);
        advertencia.setHeaderText(null);
        advertencia.setContentText(mensaje);
        advertencia.showAndWait();
    }
}
