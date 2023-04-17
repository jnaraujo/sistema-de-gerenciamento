module com.uefs.sistemadegerenciamento {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.uefs.sistemadegerenciamento to javafx.fxml;
    exports com.uefs.sistemadegerenciamento;
    exports com.uefs.sistemadegerenciamento.controllers;
}