package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.WorkOrderManagerApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class CreateCleaningServiceController extends Controller {

    @FXML
    private TextArea componentListField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField costField;

    @FXML
    private Label infoLabel;

    @FXML
    private void initialize() {
        WorkOrderManagerApplication.stage.setTitle("Adicionar novo Serviço de Limpeza");
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }

    @FXML
    private void onCreateButtonClick() {

        if(!getLoggedUser().getUserType().equals(UserType.ADMINISTRATOR)){
            error("Você não tem permissão para realizar esta ação.");
            return;
        }

        componentListField.getStyleClass().remove("error");
        priceField.getStyleClass().remove("error");
        costField.getStyleClass().remove("error");
        info("");

        if(componentListField.getText().isEmpty()) {
            componentListField.getStyleClass().add("error");
            error("Digite uma descrição.");
            return;
        }

        if(priceField.getText().isEmpty()) {
            priceField.getStyleClass().add("error");
            error("Digite um preço por unidade.");
            return;
        }

        if(costField.getText().isEmpty()) {
            costField.getStyleClass().add("error");
            error("Digite um custo por unidade.");
            return;
        }

        if(!priceField.getText().matches("[0-9]+(\\,[0-9]+)?")) { // 9,99
            priceField.getStyleClass().add("error");
            error("Digite um preço válido. (Ex: 9,99)");
            return;
        }

        if(!costField.getText().matches("[0-9]+(\\,[0-9]+)?")) { // 9,99
            costField.getStyleClass().add("error");
            error("Digite um custo válido. (Ex: 9,99)");
            return;
        }

        Double pricePerUnit = Double.parseDouble(priceField.getText().replace(",", "."));
        Double costPerUnit = Double.parseDouble(costField.getText().replace(",", "."));

        CleaningService service = new CleaningService(pricePerUnit, costPerUnit);

        List<String> componentList = List.of(componentListField.getText().split("[,\\n]+"));

        componentList
                .stream().filter(component -> !component.trim().isEmpty())
                .forEach(component -> {
                    String componentTrimmed = component.trim();
                    service.addComponent(componentTrimmed);
                });

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
