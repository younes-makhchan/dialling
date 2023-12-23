module com.exemple.dialing {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.exemple.dialing to javafx.fxml;
    exports com.exemple.dialing;
}