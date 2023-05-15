package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateInstallationServiceController extends Controller{
    private InstallationService installationService;
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField pricePerUnitField;

    @FXML
    private TextField costPerUnitField;

    @FXML
    private Label infoLabel;

    public void setInstallationService(InstallationService installationService) {
        this.installationService = installationService;

        descriptionField.setText(installationService.getDescription());
        pricePerUnitField.setText(String.valueOf(installationService.getPrice()).replace(".", ","));
        costPerUnitField.setText(String.valueOf(installationService.getCost()).replace(".", ","));
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Atualizar Serviço de Instalação");
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }

    @FXML
    private void onUpdateButtonClick() {
        descriptionField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        pricePerUnitField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        costPerUnitField.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        info("");

        if(descriptionField.getText().isEmpty()) {
            descriptionField.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite uma descrição.");
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

        Double pricePerUnit = Double.parseDouble(pricePerUnitField.getText().replace(",", "."));
        Double costPerUnit = Double.parseDouble(costPerUnitField.getText().replace(",", "."));

        installationService.setDescription(descriptionField.getText());
        installationService.setPrice(pricePerUnit);
        installationService.setCost(costPerUnit);

        DAOManager.getInstallationServiceDao().update(installationService);

        success("Serviço de instalação atualizado com sucesso.");
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
