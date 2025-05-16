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

public class RegistroController {

    @FXML private TextField campoNombre;
    @FXML private TextField campoEdad;
    @FXML private ComboBox<String> comboGenero;
    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoClave;

    @FXML
    public void initialize() {
        comboGenero.getItems().addAll("Masculino", "Femenino", "Otro");
    }

    @FXML
    public void registrar(ActionEvent evento) {
        try {
            String nombre = campoNombre.getText().trim();
            int edad = Integer.parseInt(campoEdad.getText().trim());
            String genero = comboGenero.getValue();
            String usuario = campoUsuario.getText().trim();
            String clave = campoClave.getText().trim();

            if (nombre.isEmpty() || genero == null || usuario.isEmpty() || clave.isEmpty()) {
                mostrar("Faltan datos", "Completa todos los campos.");
                return;
            }

            UsuarioService servicio = new UsuarioService();
            Persona nueva = new Persona(nombre, edad, genero, usuario, clave);
            if (servicio.registrarUsuario(nueva)) {
                mostrar("Registro exitoso", "Puedes iniciar sesión ahora");
                volverAlInicio(evento);
            } else {
                mostrar("Error", "No se pudo registrar el usuario");
            }

        } catch (NumberFormatException e) {
            mostrar("Error", "Edad inválida");
        }
    }

    @FXML
    public void volverAlInicio(ActionEvent evento) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/Login.fxml"));
            Stage stage = (Stage) campoNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            mostrar("Error", "No se pudo volver al inicio");
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
