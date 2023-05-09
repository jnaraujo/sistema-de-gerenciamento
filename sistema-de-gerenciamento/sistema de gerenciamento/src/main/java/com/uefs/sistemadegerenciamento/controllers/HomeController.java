package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.model.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class HomeController {
    User user;

    @FXML
    public Label titleLabel;

    @FXML
    public Text userRoleText;

    public void setUser(User user) {
        this.user = user;
        titleLabel.setText("Bem vindo, " + user.getName()+ "!");
        userRoleText.setText("Seu cargo atual: " + user.getUserType().toString());
    }
}
