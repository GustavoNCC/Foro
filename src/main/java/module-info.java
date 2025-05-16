module swing.foro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Foro.modelo to javafx.fxml, javafx.graphics;
    opens Foro.control to javafx.fxml;
    opens Foro.servicio to javafx.fxml;


    exports Foro.modelo;
}
