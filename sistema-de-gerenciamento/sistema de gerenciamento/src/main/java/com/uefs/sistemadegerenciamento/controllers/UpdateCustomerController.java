package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateCustomerController extends Controller {

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
        backPage();
    }

    @FXML
    private void onUpdateCustomerButtonClick() {
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
        infoLabel.setStyle("-fx-text-fill: #157315");
        infoLabel.setText(message);
    }
}
