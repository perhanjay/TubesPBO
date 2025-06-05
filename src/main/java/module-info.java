module com.myapp.demotubes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.sql;

    opens com.myapp.demotubes to javafx.fxml;
    exports com.myapp.demotubes;
    exports com.myapp.demotubes.Entities;
    opens com.myapp.demotubes.Entities to javafx.fxml;
    exports com.myapp.demotubes.Entities.Properties;
    opens com.myapp.demotubes.Entities.Properties to javafx.fxml;
    exports com.myapp.demotubes.Controller;
    opens com.myapp.demotubes.Controller to javafx.fxml;
    exports com.myapp.demotubes.Entities.Sessions;
    opens com.myapp.demotubes.Entities.Sessions to javafx.fxml;
    exports com.myapp.demotubes.Controller.DocumentController;
    opens com.myapp.demotubes.Controller.DocumentController to javafx.fxml;
}