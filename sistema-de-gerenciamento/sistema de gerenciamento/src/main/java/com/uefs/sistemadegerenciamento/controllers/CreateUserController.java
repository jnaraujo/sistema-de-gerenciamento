package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.Receptionist;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class CreateUserController {
    private User user;
    @FXML
    public TextField nameField;
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public ChoiceBox<UserType> userTypeChoiceBox;
    @FXML
    public Label infoLabel;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        HelloApplication.stage.setTitle("Criar Usuário");

        userTypeChoiceBox.getItems().addAll(UserType.values());
        userTypeChoiceBox.setConverter(new StringConverter<UserType>() {
            @Override
            public String toString(UserType userType) {
                switch (userType) {
                    case ADMINISTRATOR:
                        return "Administrador";
                    case RECEPTIONIST:
                        return "Recepcionista";
                    case TECHNICIAN:
                        return "Técnico";
                    default:
                        return "";
                }
            }

            @Override
            public UserType fromString(String s) {
                switch (s) {
                    case "Administrador":
                        return UserType.ADMINISTRATOR;
                    case "Recepcionista":
                        return UserType.RECEPTIONIST;
                    case "Técnico":
                        return UserType.TECHNICIAN;
                    default:
                        return null;
                }
            }
        });
        userTypeChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onBackButtonClick() {
        PageLoader.goHome(user);
    }

    @FXML
    public void onCreateCustomerButtonClick() {
        nameField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        emailField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        passwordField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        userTypeChoiceBox.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        info("");

        if(nameField.getText().isEmpty()) {
            nameField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um nome.");
            return;
        }

        if(emailField.getText().isEmpty()) {
            emailField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um email.");
            return;
        }

        if(passwordField.getText().isEmpty()) {
            passwordField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um telefone.");
            return;
        }

        if(userTypeChoiceBox.getValue() == null) {
            userTypeChoiceBox.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um endereço.");
            return;
        }
        User user = userFactory(nameField.getText(), emailField.getText(), passwordField.getText(), userTypeChoiceBox.getValue());

        DAOManager.getUserDao().save(user);

        success("Cliente criado com sucesso!");

        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        userTypeChoiceBox.getSelectionModel().selectFirst();

        nameField.requestFocus();
    }

    private User userFactory(String name, String email, String password, UserType userType) {
        switch (userType) {
            case ADMINISTRATOR:
                return new Administrator(name, email, password);
            case TECHNICIAN:
                return new Technician(name, email, password);
            case RECEPTIONIST:
                return new Receptionist(name, email, password);
            default:
                return null;
        }
    }

    private void info(String message) {
        infoLabel.setStyle("-fx-text-fill: #000000");
        infoLabel.setText(message);
    }

    private void error(String message) {
        infoLabel.setStyle("-fx-text-fill: #ff0000");
        infoLabel.setText(message);
    }

    private void success(String message) {
        infoLabel.setStyle("-fx-text-fill: #00ff00");
        infoLabel.setText(message);
    }
}
