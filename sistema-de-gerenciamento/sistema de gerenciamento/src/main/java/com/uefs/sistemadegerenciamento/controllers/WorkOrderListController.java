package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.model.user.User;
import javafx.fxml.FXML;

public class WorkOrderListController {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        HelloApplication.stage.setTitle("Lista de ordens de serviço");
    }

    @FXML
    public void onBackButtonClick() {
        System.out.println("Back button clicked");
    }
}
