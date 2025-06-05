package Foro.control;

import Foro.modelo.Entrada;
import Foro.modelo.Grupo;
import Foro.modelo.Persona;
import Foro.servicio.EntradaService;
import Foro.servicio.GrupoService;
import Foro.servicio.ReaccionService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;

public class PublicacionController {

    @FXML private TextArea campoContenido;
    @FXML private CheckBox chkAdulto;
    @FXML private CheckBox chkNSFW;
    @FXML private ComboBox<Grupo> comboGrupos;
    @FXML private Button btnLike;
    @FXML private Button btnRepost;

    private Persona personaActiva;
    private MuroController muroOrigen;
    private Entrada entradaMostrada;

    public void setContexto(MuroController muro, Persona persona) {
        this.muroOrigen = muro;
        this.personaActiva = persona;
        cargarGrupos();
    }

    private void cargarGrupos() {
        GrupoService servicio = new GrupoService();
        List<Grupo> grupos = servicio.buscarTodos();
        comboGrupos.getItems().add(null);
        comboGrupos.getItems().addAll(grupos);
        comboGrupos.setPromptText("Selecciona un grupo (opcional)");
    }

    @FXML
    public void publicar() {
        String contenido = campoContenido.getText().trim();
        if (contenido.isEmpty()) {
            mostrar("Error", "No puedes publicar un contenido vacío.");
            return;
        }

        Entrada entrada = new Entrada(contenido, chkAdulto.isSelected(), chkNSFW.isSelected());
        entrada.setFecha(new Date());

        Grupo grupo = comboGrupos.getValue();
        if (grupo != null) {
            entrada.setIdGrupo(grupo.getId());
            entrada.setNombreGrupo(grupo.getNombre());
        }

        EntradaService servicio = new EntradaService();
        if (servicio.guardar(entrada, personaActiva)) {
            muroOrigen.setPersonaActiva(personaActiva);
            cerrar();
        } else {
            mostrar("Error", "No se pudo publicar.");
        }
    }

    @FXML
    public void cancelar() {
        cerrar();
    }

    public void setEntradaExistente(MuroController muro, Persona persona, Entrada entrada) {
        this.muroOrigen = muro;
        this.personaActiva = persona;
        this.entradaMostrada = entrada;

        campoContenido.setText(entrada.getContenido());
        campoContenido.setEditable(false);

        chkAdulto.setSelected(entrada.isEsAdulto());
        chkNSFW.setSelected(entrada.isEsNsfw());
        chkAdulto.setDisable(true);
        chkNSFW.setDisable(true);
        comboGrupos.setVisible(false);

        btnLike.setVisible(true);
        btnRepost.setVisible(true);
    }

    @FXML
    public void darLike() {
        if (entradaMostrada == null) return;
        ReaccionService servicio = new ReaccionService();
        boolean exito = servicio.registrar(personaActiva.getId(), entradaMostrada.getId(), "LIKE");
        mostrar(exito ? "Like dado" : "Error", exito ? "Te gustó esta publicación." : "No se pudo dar like.");
    }

    @FXML
    public void darRepost() {
        if (entradaMostrada == null) return;

        Entrada repost = new Entrada(
                entradaMostrada.getContenido(),
                entradaMostrada.isEsAdulto(),
                entradaMostrada.isEsNsfw()
        );
        repost.setOrigen(entradaMostrada.getAutor());
        repost.setFecha(new Date());

        EntradaService servicio = new EntradaService();
        if (servicio.guardar(repost, personaActiva)) {
            mostrar("Repost exitoso", "Reposteaste esta publicación.");
            muroOrigen.setPersonaActiva(personaActiva);
            cerrar();
        } else {
            mostrar("Error", "No se pudo hacer repost.");
        }
    }

    private void cerrar() {
        Stage stage = (Stage) campoContenido.getScene().getWindow();
        stage.close();
    }

    private void mostrar(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}


