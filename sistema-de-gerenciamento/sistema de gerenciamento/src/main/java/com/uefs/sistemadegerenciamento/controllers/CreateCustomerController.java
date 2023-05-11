package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.Comparator;
import java.util.List;

public class CreateCustomerController {
    private User user;

    @FXML
    public TextField nameField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField phoneField;

    @FXML
    public TextField addressField;

    @FXML
    public Label infoLabel;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        HelloApplication.stage.setTitle("Criar Cliente");
    }

    @FXML
    public void onBackButtonClick() {
        PageLoader.goHome(user);
    }

    @FXML
    public void onCreateCustomerButtonClick() {
        nameField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        emailField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        phoneField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        addressField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
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

        if(phoneField.getText().isEmpty()) {
            phoneField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um telefone.");
            return;
        }

        if(addressField.getText().isEmpty()) {
            addressField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um endereço.");
            return;
        }

        Customer customer = new Customer(
                nameField.getText(),
                addressField.getText(),
                phoneField.getText(),
                emailField.getText()
        );

        DAOManager.getCustomerDao().save(customer);

        success("Cliente criado com sucesso!");

        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");

        nameField.requestFocus();
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
