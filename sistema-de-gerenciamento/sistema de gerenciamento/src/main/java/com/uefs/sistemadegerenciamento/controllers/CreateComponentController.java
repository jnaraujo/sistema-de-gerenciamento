package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateComponentController {
    private User loggedUser;

    @FXML
    private TextField nameField;

    @FXML
    private TextField pricePerUnitField;

    @FXML
    private TextField costPerUnitField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField manufacturerField;

    @FXML
    private Label infoLabel;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Adicionar novo componente");
    }

    @FXML
    private void onBackButtonClick() {
        PageLoader.goHome(loggedUser);
    }

    @FXML
    private void onCreateComponentButtonClick() {
        nameField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        pricePerUnitField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        costPerUnitField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        quantityField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        manufacturerField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        info("");

        if(nameField.getText().isEmpty()) {
            nameField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um nome.");
            return;
        }

        if(manufacturerField.getText().isEmpty()) {
            manufacturerField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um fabricante.");
            return;
        }

        if(pricePerUnitField.getText().isEmpty()) {
            pricePerUnitField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um preço por unidade.");
            return;
        }

        if(costPerUnitField.getText().isEmpty()) {
            costPerUnitField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um custo por unidade.");
            return;
        }

        if(quantityField.getText().isEmpty()) {
            quantityField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite uma quantidade.");
            return;
        }

        if(!pricePerUnitField.getText().matches("[0-9]+(\\,[0-9]+)?")) { // 9,99
            pricePerUnitField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um preço válido. (Ex: 9,99)");
            return;
        }

        if(!costPerUnitField.getText().matches("[0-9]+(\\,[0-9]+)?")) { // 9,99
            costPerUnitField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um custo válido. (Ex: 9,99)");
            return;
        }

        if(!quantityField.getText().matches("[0-9]+")) {
            quantityField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite uma quantidade válida. (Ex: 9)");
            return;
        }

        Double pricePerUnit = Double.parseDouble(pricePerUnitField.getText().replace(",", "."));
        Double costPerUnit = Double.parseDouble(costPerUnitField.getText().replace(",", "."));
        int quantity = Integer.parseInt(quantityField.getText());

        ComputerComponent component = new ComputerComponent(
                nameField.getText(),
                manufacturerField.getText(),
                pricePerUnit,
                costPerUnit,
                quantity
        );

        DAOManager.getInventoryDao().save(component);

        success("Componente adicionado com sucesso!");

        nameField.setText("");
        manufacturerField.setText("");
        pricePerUnitField.setText("");
        costPerUnitField.setText("");
        quantityField.setText("");
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
