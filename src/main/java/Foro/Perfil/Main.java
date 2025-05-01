package Foro.Perfil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage ventanaPrincipal) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/InicioPrincipal.fxml"));
            Parent interfazInicio = cargador.load();

            Scene escena = new Scene(interfazInicio, 600, 400);
            ventanaPrincipal.setScene(escena);
            ventanaPrincipal.setTitle("Inicio de Sesión");
            ventanaPrincipal.show();
        } catch (Exception e) {
            System.err.println("No se pudo cargar la interfaz inicial: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] argumentos) {
        launch(argumentos);
    }
}
