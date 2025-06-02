package Foro.control;

import Foro.modelo.Grupo;
import Foro.modelo.Persona;
import Foro.servicio.GrupoService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Date;

public class ComunidadController {

    @FXML private TextField campoNombre;
    @FXML private TextArea campoDescripcion;
    @FXML private ListView<Grupo> listaComunidades;
    @FXML private Button btnVerMiembros;


    private Persona personaActiva;

    public void setPersonaActiva(Persona persona) {
        this.personaActiva = persona;
    }

    @FXML
    public void crearComunidad() {
        String nombre = campoNombre.getText().trim();
        String descripcion = campoDescripcion.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            mostrar("Error", "Pon todos los campos.");
            return;
        }

        Grupo grupo = new Grupo(nombre, descripcion, new Date(), personaActiva.getApodo());
        GrupoService servicio = new GrupoService();

        if (servicio.crear(grupo, personaActiva.getId())) {
            mostrar("Éxito", "Comunidad creada correctamente.");
            cerrar();
        } else {
            mostrar("Error", "No se pudo crear la comunidad.");
        }
    }

    private void cerrar() {
        Stage stage = (Stage) campoNombre.getScene().getWindow();
        stage.close();
    }

    private void mostrar(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    @FXML
    public void verMiembrosSeleccionados() {
        Grupo seleccionado = listaComunidades.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Sin selección");
            a.setContentText("Selecciona una comunidad primero.");
            a.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MiembrosGrupo.fxml"));
            Parent root = loader.load();

            MiembrosGrupoController ctrl = loader.getController();
            ctrl.cargar(seleccionado.getId(), seleccionado.getNombre());

            Stage stage = new Stage();
            stage.setTitle("Miembros de " + seleccionado.getNombre());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
