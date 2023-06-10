package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.WorkOrderManagerApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateInstallationServiceController extends Controller {

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField pricePerUnitField;

    @FXML
    private TextField costPerUnitField;

    @FXML
    private Label infoLabel;

    @FXML
    private void initialize() {
        WorkOrderManagerApplication.stage.setTitle("Adicionar novo Serviço de Instalação");
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

        descriptionField.getStyleClass().remove("error");;
        pricePerUnitField.getStyleClass().remove("error");;
        costPerUnitField.getStyleClass().remove("error");;
        info("");

        if(descriptionField.getText().isEmpty()) {
            descriptionField.getStyleClass().add("error");
            error("Digite uma descrição.");
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

        Double pricePerUnit = Double.parseDouble(pricePerUnitField.getText().replace(",", "."));
        Double costPerUnit = Double.parseDouble(costPerUnitField.getText().replace(",", "."));

        InstallationService service = new InstallationService(
                descriptionField.getText(),
                pricePerUnit,
                costPerUnit
        );

        DAOManager.getInstallationServiceDao().save(service);

        success("Serviço de instalação adicionado com sucesso!");

        descriptionField.setText("");
        pricePerUnitField.setText("");
        costPerUnitField.setText("");
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
