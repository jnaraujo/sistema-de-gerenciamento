package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public Label titleText;

    @FXML
    public TextField emailField;

    @FXML
    public PasswordField passwordField;

    private User user;

    @FXML
    public void onLoginClick()  {
        emailField.styleProperty().setValue("");
        passwordField.styleProperty().setValue("");

        if(emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            if(emailField.getText().isEmpty())
                emailField.styleProperty().setValue("-fx-border-color: red");
            if(passwordField.getText().isEmpty())
                passwordField.styleProperty().setValue("-fx-border-color: red");

            titleText.setText("Preencha todos os campos");
            return;
        }

        User user = DAOManager.getUserDao().findByEmail(emailField.getText());

        if(user == null) {
            titleText.setText("Usuário não encontrado");
            return;
        }

        if(!user.getPassword().equals(passwordField.getText())) {
            titleText.setText("Senha incorreta");
            return;
        }

        this.user = user;
        this.openPage("home.fxml");
    }

    private void openPage(String url) {
        try{
            FXMLLoader fxmlLoader = PageLoader.load(url);
            Parent root = fxmlLoader.load();

            HelloApplication.stage.setTitle("Home - Sistema de Gerenciamento");

            HomeController homeController = fxmlLoader.getController();
            homeController.setUser(user);

            MainController.staticRoot.getChildren().clear();
            MainController.staticRoot.getChildren().add(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
