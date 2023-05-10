package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;

public class CreateWorkOrderController {
    User user;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        HelloApplication.stage.setTitle("Criar Ordem de Serviço");
    }

    @FXML
    public void onBackButtonClick() {
        PageLoader.goHome(user);
    }
}
