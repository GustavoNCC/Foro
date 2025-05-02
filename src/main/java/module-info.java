module swing.foro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Foro.Perfil to javafx.fxml, javafx.graphics;
    opens Foro.Controllers to javafx.fxml;
    opens Foro.SQL_Conexion to javafx.fxml;


    exports Foro.Perfil;
}
