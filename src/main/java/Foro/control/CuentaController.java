package Foro.control;

import Foro.modelo.Persona;
import Foro.servicio.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CuentaController {

    @FXML private PasswordField campoActual;
    @FXML private PasswordField campoNueva;

    private Persona personaActiva;

    public void setPersona(Persona persona) {
        this.personaActiva = persona;
    }

    @FXML
    public void cambiarClave() {
        String actual = campoActual.getText().trim();
        String nueva = campoNueva.getText().trim();

        if (!personaActiva.getClave().equals(actual)) {
            mostrar("Error", "La contraseña actual es incorrecta.");
            return;
        }

        if (nueva.length() < 6) {
            mostrar("Error", "La nueva contraseña debe tener al menos 6 caracteres.");
            return;
        }

        UsuarioService servicio = new UsuarioService();
        boolean exito = servicio.cambiarClave(personaActiva.getId(), nueva);

        if (exito) {
            personaActiva.setClave(nueva);
            mostrar("Éxito", "Contraseña actualizada correctamente.");
            campoActual.clear();
            campoNueva.clear();
        } else {
            mostrar("Error", "No se pudo cambiar la contraseña.");
        }
    }

    @FXML
    public void eliminarCuenta() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Cuenta");
        alert.setHeaderText("¿Estás segura de eliminar tu cuenta?");
        alert.setContentText("Esta acción es irreversible.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            UsuarioService servicio = new UsuarioService();
            boolean exito = servicio.eliminarCuenta(personaActiva.getId());

            if (exito) {
                mostrar("Cuenta eliminada", "Tu cuenta ha sido borrada.");
                volverAlInicio();
            } else {
                mostrar("Error", "No se pudo eliminar la cuenta.");
            }
        }
    }

    private void volverAlInicio() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/Login.fxml"));
            Stage stage = (Stage) campoActual.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        } catch (Exception e) {
            e.printStackTrace();
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
