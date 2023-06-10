package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.WorkOrderManagerApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateCustomerController extends Controller {
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private Label infoLabel;

    @FXML
    private void initialize() {
        WorkOrderManagerApplication.stage.setTitle("Criar Cliente");
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }

    @FXML
    private void onCreateCustomerButtonClick() {
        nameField.getStyleClass().remove("error");;
        emailField.getStyleClass().remove("error");;
        phoneField.getStyleClass().remove("error");;
        addressField.getStyleClass().remove("error");;
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

        if(phoneField.getText().isEmpty()) {
            phoneField.getStyleClass().add("error");
            error("Digite um telefone.");
            return;
        }

        if(addressField.getText().isEmpty()) {
            addressField.getStyleClass().add("error");
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
        infoLabel.setStyle("-fx-text-fill: #157315");
        infoLabel.setText(message);
    }
}
