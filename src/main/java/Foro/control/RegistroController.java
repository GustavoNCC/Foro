package Foro.control;

import Foro.servicio.GestorUsuarios;
import Foro.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistroController {

    @FXML
    private TextField inputNombre;
    @FXML
    private TextField inputEdad;
    @FXML
    private ComboBox<String> selectorGenero;
    @FXML
    private TextField inputCorreo;
    @FXML
    private TextField inputUsuario;
    @FXML
    private PasswordField inputClave;

    @FXML
    private void initialize() {
        cargarOpcionesGenero();
    }

    @FXML
    private void procesarRegistro(ActionEvent evento) {
        if (!datosValidos()) return;

        Usuario nuevo = construirUsuarioDesdeFormulario();
        GestorUsuarios gestor = new GestorUsuarios();

        try {
            if (gestor.usuarioYaExiste(nuevo.getUsername())) {
                notificar("Error", "El nombre de usuario ya está en uso");
                return;
            }

            if (gestor.registrarNuevoUsuario(nuevo)) {
                notificar("Éxito", "Usuario registrado correctamente");
                redirigirAlLogin(evento);
            } else {
                notificar("Error", "No se pudo registrar el usuario");
            }

        } catch (Exception e) {
            notificar("Error", "Error durante el registro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void redirigirAlLogin(ActionEvent evento) {
        cambiarVista("/InicioPrincipal.fxml", "Inicio de Sesión", 600, 400);
    }

    private void cargarOpcionesGenero() {
        selectorGenero.getItems().addAll("Masculino", "Femenino");
    }

    private boolean datosValidos() {
        String nombre = inputNombre.getText().trim();
        String edadTexto = inputEdad.getText().trim();
        String genero = selectorGenero.getValue();
        String correo = inputCorreo.getText().trim();
        String usuario = inputUsuario.getText().trim();
        String clave = inputClave.getText().trim();

        if (nombre.isEmpty() || edadTexto.isEmpty() || genero == null || correo.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
            notificar("Error", "Todos los campos son obligatorios");
            return false;
        }

        try {
            int edad = Integer.parseInt(edadTexto);
            if (edad <= 0) {
                notificar("Error", "La edad debe ser un número positivo");
                return false;
            }
        } catch (NumberFormatException e) {
            notificar("Error", "La edad debe ser un número válido");
            return false;
        }

        return true;
    }

    private Usuario construirUsuarioDesdeFormulario() {
        String nombre = inputNombre.getText().trim();
        int edad = Integer.parseInt(inputEdad.getText().trim());
        String genero = selectorGenero.getValue();
        String correo = inputCorreo.getText().trim();
        String usuario = inputUsuario.getText().trim();
        String clave = inputClave.getText().trim();

        return new Usuario(nombre, edad, genero, correo, usuario, clave);
    }

    private void cambiarVista(String ruta, String titulo, int ancho, int alto) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(ruta));
            Stage ventana = (Stage) inputNombre.getScene().getWindow();
            ventana.setScene(new Scene(vista, ancho, alto));
            ventana.setTitle(titulo);
        } catch (Exception e) {
            notificar("Error", "No se pudo cargar la vista: " + titulo);
        }
    }

    private void notificar(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
