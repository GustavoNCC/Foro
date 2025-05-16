module swing.foro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Foro.launch to javafx.graphics;
    opens Foro.control to javafx.fxml;
    opens Foro.modelo to javafx.base;
    opens Foro.servicio to javafx.base;

    exports Foro.launch;
    exports Foro.control;
    exports Foro.modelo;
    exports Foro.servicio;
    exports Foro.acceso;
    opens Foro.acceso to javafx.base;
}
