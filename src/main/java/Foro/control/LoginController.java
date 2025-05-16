package Foro.control;

import Foro.modelo.Persona;
import Foro.servicio.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoClave;

    @FXML
    public void iniciarSesion(ActionEvent evento) {
        String usuario = campoUsuario.getText().trim();
        String clave = campoClave.getText().trim();

        if (usuario.isEmpty() || clave.isEmpty()) {
            mostrarAlerta("Faltan datos", "Completa ambos campos.");
            return;
        }

        UsuarioService servicio = new UsuarioService();
        Persona persona = servicio.autenticar(usuario, clave);

        if (persona != null) {
            cargarVistaMuro(persona);
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas");
        }
    }

    private void cargarVistaMuro(Persona persona) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Muro.fxml"));
            Parent raiz = loader.load();

            MuroController controlador = loader.getController();
            controlador.setPersonaActiva(persona);

            Stage escena = (Stage) campoUsuario.getScene().getWindow();
            escena.setScene(new Scene(raiz));
            escena.setTitle("Red Felina - Muro de @" + persona.getApodo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void irARegistro(ActionEvent evento) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/Registro.fxml"));
            Stage stage = (Stage) campoUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de registro");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
