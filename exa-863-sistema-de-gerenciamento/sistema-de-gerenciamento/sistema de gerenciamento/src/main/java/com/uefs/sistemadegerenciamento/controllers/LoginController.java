package com.uefs.sistemadegerenciamento.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    public Label welcomeText;

    @FXML
    public void onLoginClick() {
        welcomeText.setText("Hello, JavaFX!");
    }
}
