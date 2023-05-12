package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateCustomerController {
    private User loggedUser;

    private Customer updatedCustomer;

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

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void setUpdatedCustomer(Customer updatedCustomer) {
        this.updatedCustomer = updatedCustomer;

        nameField.setText(updatedCustomer.getName());
        emailField.setText(updatedCustomer.getEmail());
        phoneField.setText(updatedCustomer.getPhone());
        addressField.setText(updatedCustomer.getAddress());
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Atualizar Cliente");
    }

    @FXML
    private void onBackButtonClick() {
        ManageCustomersController controller = (ManageCustomersController) PageLoader.openPage("manage_customers.fxml");
        controller.setLoggedUser(loggedUser);
    }

    @FXML
    private void onUpdateCustomerButtonClick() {
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

        updatedCustomer.setName(nameField.getText());
        updatedCustomer.setEmail(emailField.getText());
        updatedCustomer.setPhone(phoneField.getText());
        updatedCustomer.setAddress(addressField.getText());

        DAOManager.getCustomerDao().update(updatedCustomer);

        success("Cliente atualizado com sucesso!");
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
