package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class CreateCleaningServiceController {
    private User loggedUser;

    @FXML
    private TextArea componentListField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField costField;

    @FXML
    private Label infoLabel;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Adicionar novo Serviço de Limpeza");
    }

    @FXML
    private void onBackButtonClick() {
        PageLoader.goHome(loggedUser);
    }

    @FXML
    private void onCreateButtonClick() {
        componentListField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        priceField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        costField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        info("");

        if(componentListField.getText().isEmpty()) {
            componentListField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite uma descrição.");
            return;
        }

        if(priceField.getText().isEmpty()) {
            priceField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um preço por unidade.");
            return;
        }

        if(costField.getText().isEmpty()) {
            costField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um custo por unidade.");
            return;
        }

        if(!priceField.getText().matches("[0-9]+(\\,[0-9]+)?")) { // 9,99
            priceField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um preço válido. (Ex: 9,99)");
            return;
        }

        if(!costField.getText().matches("[0-9]+(\\,[0-9]+)?")) { // 9,99
            costField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite um custo válido. (Ex: 9,99)");
            return;
        }

        Double pricePerUnit = Double.parseDouble(priceField.getText().replace(",", "."));
        Double costPerUnit = Double.parseDouble(costField.getText().replace(",", "."));

        CleaningService service = new CleaningService(pricePerUnit, costPerUnit);

        List<String> componentList = List.of(componentListField.getText().split("[,\\n]+"));
        componentList.forEach(service::addComponent);

        DAOManager.getCleaningServiceDao().save(service);

        success("Serviço de Limpeza adicionado com sucesso!");

        componentListField.setText("");
        priceField.setText("");
        costField.setText("");
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
