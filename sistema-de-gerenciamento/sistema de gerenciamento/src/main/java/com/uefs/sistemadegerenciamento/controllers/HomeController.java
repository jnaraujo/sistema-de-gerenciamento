package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.model.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {
    User user;

    @FXML
    public Label titleText;

    public void setUser(User user) {
        this.user = user;
        titleText.setText("Bem vindo, " + user.getName()+ "!");
    }
}
