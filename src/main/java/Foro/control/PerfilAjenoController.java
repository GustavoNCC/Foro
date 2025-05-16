package Foro.control;


import Foro.modelo.Persona;
import Foro.servicio.RelacionService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PerfilAjenoController {

    @FXML private Label lblTitulo, lblNombre, lblEdad, lblGenero, lblUsuario, lblBio;
    @FXML private Button btnAmigo, btnBloqueo;

    private Persona personaActiva;
    private Persona objetivo;

    public void setDatos(Persona actual, Persona objetivo) {
        this.personaActiva = actual;
        this.objetivo = objetivo;

        lblTitulo.setText("Perfil de @" + objetivo.getApodo());
        lblNombre.setText("Nombre: " + objetivo.getNombreCompleto());
        lblEdad.setText("Edad: " + objetivo.getAnios());
        lblGenero.setText("Género: " + objetivo.getIdentidadGenero());
        lblUsuario.setText("Usuario: @" + objetivo.getApodo());
        lblBio.setText((objetivo.getDescripcion() == null || objetivo.getDescripcion().isEmpty())
                ? "(sin biografía)" : objetivo.getDescripcion());
    }

    @FXML
    public void agregarAmigo() {
        RelacionService servicio = new RelacionService();
        boolean exito = servicio.registrarRelacion(personaActiva.getId(), objetivo.getId(), "AMIGO");
        mostrarAlerta(exito ? "Ahora son amigas" : "Error", exito ? "Has agregado a esta persona." : "No se pudo agregar.");
    }

    @FXML
    public void bloquearUsuario() {
        RelacionService servicio = new RelacionService();
        boolean exito = servicio.registrarRelacion(personaActiva.getId(), objetivo.getId(), "BLOQUEADO");
        mostrarAlerta(exito ? "Bloqueado" : "Error", exito ? "Usuario bloqueado." : "No se pudo bloquear.");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}
