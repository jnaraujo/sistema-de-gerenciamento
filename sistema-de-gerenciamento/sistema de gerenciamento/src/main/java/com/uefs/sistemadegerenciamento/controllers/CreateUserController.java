package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.WorkOrderManagerApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.Receptionist;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.converter.UserTypeConverter;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateUserController extends Controller {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<UserType> userTypeChoiceBox;
    @FXML
    private Label infoLabel;

    @FXML
    private void initialize() {
        WorkOrderManagerApplication.stage.setTitle("Criar Usuário");

        userTypeChoiceBox.getItems().addAll(UserType.values());
        userTypeChoiceBox.setConverter(new UserTypeConverter());
        userTypeChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }

    @FXML
    private void onCreateUserButtonClick() {
        nameField.getStyleClass().remove("error");;
        emailField.getStyleClass().remove("error");;
        passwordField.getStyleClass().remove("error");;
        userTypeChoiceBox.getStyleClass().remove("error");;
        info("");

        if(nameField.getText().isEmpty()) {
            nameField.getStyleClass().add("error");
            error("Digite um nome.");
            return;
        }

        if(emailField.getText().isEmpty()) {
            emailField.getStyleClass().add("error");
            error("Digite um email.");
            return;
        }

        if(passwordField.getText().isEmpty()) {
            passwordField.getStyleClass().add("error");
            error("Digite um telefone.");
            return;
        }

        if(userTypeChoiceBox.getValue() == null) {
            userTypeChoiceBox.getStyleClass().add("error");
            error("Digite um endereço.");
            return;
        }
        User user = userFactory(nameField.getText(), emailField.getText(), passwordField.getText(), userTypeChoiceBox.getValue());

        boolean doesEmailAlreadyExists = DAOManager.getUserDao().findByEmail(user.getEmail()) != null;

        if(doesEmailAlreadyExists) {
            emailField.getStyleClass().add("error");
            error("Email já cadastrado.");
            return;
        }

        DAOManager.getUserDao().save(user);

        success("Usuário criado com sucesso!");

        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        userTypeChoiceBox.getSelectionModel().selectFirst();

        nameField.requestFocus();
    }

    private User userFactory(String name, String email, String password, UserType userType) {
        return switch (userType) {
            case ADMINISTRATOR -> new Administrator(name, email, password);
            case RECEPTIONIST -> new Receptionist(name, email, password);
            case TECHNICIAN -> new Technician(name, email, password);
        };
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
        infoLabel.setStyle("-fx-text-fill: #157315");
        infoLabel.setText(message);
    }
}
