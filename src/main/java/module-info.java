module com.exemple.dialing {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;

    opens com.exemple.dialing to javafx.fxml;
    opens com.exemple.dialing.presentation to javafx.fxml;
    opens com.exemple.dialing.presentation.controllers to javafx.fxml;
    opens com.exemple.dialing.dao to javafx.base;
    opens com.exemple.dialing.dao.entities to javafx.base;
    exports com.exemple.dialing;
    exports com.exemple.dialing.presentation;
}