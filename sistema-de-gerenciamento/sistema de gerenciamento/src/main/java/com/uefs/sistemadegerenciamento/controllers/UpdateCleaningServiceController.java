package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.WorkOrderManagerApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class UpdateCleaningServiceController extends Controller {
    private CleaningService service;

    @FXML
    private TextArea componentListField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField costField;

    @FXML
    private Label infoLabel;

    public void setService(CleaningService service) {
        this.service = service;

        String components = service.getComponents().stream().reduce("", (acc, component) -> {
            return acc + component + ", ";
        });
        components = components.substring(0, components.length() - 2);

        componentListField.setText(components);
        priceField.setText(String.valueOf(service.getPrice()).replace(".", ","));
        costField.setText(String.valueOf(service.getCost()).replace(".", ","));
    }

    @FXML
    private void initialize() {
        WorkOrderManagerApplication.stage.setTitle("Atualizar Serviço de Limpeza");
    }

    @FXML
    private void onBackButtonClick() {
        this.backPage();
    }

    @FXML
    private void onUpdateButtonClick() {
        componentListField.getStyleClass().remove("error");;
        priceField.getStyleClass().remove("error");;
        costField.getStyleClass().remove("error");;
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

        service.setPrice(pricePerUnit);
        service.setCost(costPerUnit);

        service.getComponents().clear();

        List<String> componentList = List.of(componentListField.getText().split("[,\\n]+"));
        componentList
                .stream().filter(component -> !component.trim().isEmpty())
                .forEach(component -> {
                    String componentTrimmed = component.trim();
                    service.addComponent(componentTrimmed);
                });

        DAOManager.getCleaningServiceDao().update(service);

        success("Serviço de Limpeza atualizado com sucesso!");
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
