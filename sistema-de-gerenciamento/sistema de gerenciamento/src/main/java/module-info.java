module com.uefs.sistemadegerenciamento {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.uefs.sistemadegerenciamento.dao;
    opens com.uefs.sistemadegerenciamento.dao.customer;
    opens com.uefs.sistemadegerenciamento.dao.cleaning_service;
    opens com.uefs.sistemadegerenciamento.dao.inventory;
    opens com.uefs.sistemadegerenciamento.dao.user;
    opens com.uefs.sistemadegerenciamento.dao.installation_service;
    opens com.uefs.sistemadegerenciamento.dao.workorder;

    opens com.uefs.sistemadegerenciamento.model;
    opens com.uefs.sistemadegerenciamento.model.user;
    opens com.uefs.sistemadegerenciamento.model.component;
    opens com.uefs.sistemadegerenciamento.model.service;

    opens com.uefs.sistemadegerenciamento to javafx.fxml;
    exports com.uefs.sistemadegerenciamento;
    exports com.uefs.sistemadegerenciamento.controllers;
}