package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateComponentController extends Controller {
    private ComputerComponent computerComponent;

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

    public void setComputerComponent(ComputerComponent computerComponent) {
        this.computerComponent = computerComponent;

        nameField.setText(computerComponent.getName());
        pricePerUnitField.setText(String.valueOf(computerComponent.getPricePerUnit()).replace(".", ","));
        costPerUnitField.setText(String.valueOf(computerComponent.getCostPerUnit()).replace(".", ","));
        quantityField.setText(String.valueOf(computerComponent.getQuantity()));
        manufacturerField.setText(computerComponent.getManufacturer());
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Atualizar componente");
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }

    @FXML
    private void onUpdateComponentButtonClick() {
        nameField.getStyleClass().remove("error");;
        pricePerUnitField.getStyleClass().remove("error");;
        costPerUnitField.getStyleClass().remove("error");;
        quantityField.getStyleClass().remove("error");;
        manufacturerField.getStyleClass().remove("error");;
        info("");

        if(nameField.getText().isEmpty()) {
            nameField.getStyleClass().add("error");
            error("Digite um nome.");
            return;
        }

        if(manufacturerField.getText().isEmpty()) {
            manufacturerField.getStyleClass().add("error");
            error("Digite um fabricante.");
            return;
        }

        if(pricePerUnitField.getText().isEmpty()) {
            pricePerUnitField.getStyleClass().add("error");
            error("Digite um preço por unidade.");
            return;
        }

        if(costPerUnitField.getText().isEmpty()) {
            costPerUnitField.getStyleClass().add("error");
            error("Digite um custo por unidade.");
            return;
        }

        if(quantityField.getText().isEmpty()) {
            quantityField.getStyleClass().add("error");
            error("Digite uma quantidade.");
            return;
        }

        if(!pricePerUnitField.getText().matches("[0-9]+(\\,[0-9]+)?")) { // 9,99
            pricePerUnitField.getStyleClass().add("error");
            error("Digite um preço válido. (Ex: 9,99)");
            return;
        }

        if(!costPerUnitField.getText().matches("[0-9]+(\\,[0-9]+)?")) { // 9,99
            costPerUnitField.getStyleClass().add("error");
            error("Digite um custo válido. (Ex: 9,99)");
            return;
        }

        if(!quantityField.getText().matches("[0-9]+")) {
            quantityField.getStyleClass().add("error");
            error("Digite uma quantidade válida. (Ex: 9)");
            return;
        }

        Double pricePerUnit = Double.parseDouble(pricePerUnitField.getText().replace(",", "."));
        Double costPerUnit = Double.parseDouble(costPerUnitField.getText().replace(",", "."));
        int quantity = Integer.parseInt(quantityField.getText());

        computerComponent.setName(nameField.getText());
        computerComponent.setManufacturer(manufacturerField.getText());
        computerComponent.setPricePerUnit(pricePerUnit);
        computerComponent.setCostPerUnit(costPerUnit);
        computerComponent.setQuantity(quantity);

        DAOManager.getInventoryDao().update(computerComponent);

        success("Componente atualizado com sucesso!");
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
