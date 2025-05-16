package Foro.control;

import Foro.modelo.Persona;
import Foro.servicio.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PerfilController {

    @FXML private Label lblTitulo, lblNombre, lblEdad, lblGenero, lblUsuario, lblBio;
    @FXML private TextArea campoBio;

    private Persona persona;

    public void mostrarPerfil(Persona persona) {
        this.persona = persona;
        lblTitulo.setText("Perfil de @" + persona.getApodo());
        lblNombre.setText("Nombre: " + persona.getNombreCompleto());
        lblEdad.setText("Edad: " + persona.getAnios());
        lblGenero.setText("Género: " + persona.getIdentidadGenero());
        lblUsuario.setText("Usuario: @" + persona.getApodo());

        String bio = persona.getDescripcion();
        lblBio.setText((bio == null || bio.isEmpty()) ? "(sin biografía)" : bio);
        campoBio.setText(bio != null ? bio : "");
    }

    @FXML
    public void actualizarBio() {
        String nueva = campoBio.getText().trim();
        UsuarioService servicio = new UsuarioService();

        if (servicio.actualizarBiografia(persona.getId(), nueva)) {
            persona.setDescripcion(nueva);
            lblBio.setText(nueva.isEmpty() ? "(sin biografía)" : nueva);
            mostrar("Éxito", "Biografía actualizada correctamente.");
        } else {
            mostrar("Error", "No se pudo actualizar la biografía.");
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
