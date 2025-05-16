package Foro.control;

import Foro.modelo.Persona;
import Foro.servicio.GrupoService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class MiembrosGrupoController {

    @FXML private Label tituloGrupo;
    @FXML private ListView<String> listaMiembros;

    public void cargar(int idGrupo, String nombreGrupo) {
        tituloGrupo.setText("Miembros de: " + nombreGrupo);
        GrupoService servicio = new GrupoService();
        List<Persona> miembros = servicio.obtenerMiembros(idGrupo);

        listaMiembros.getItems().clear();

        if (miembros.isEmpty()) {
            listaMiembros.getItems().add("Esta comunidad está vacía.");
        } else {
            for (Persona p : miembros) {
                listaMiembros.getItems().add("@" + p.getApodo() + " - " + p.getNombreCompleto());
            }
        }
    }
}
