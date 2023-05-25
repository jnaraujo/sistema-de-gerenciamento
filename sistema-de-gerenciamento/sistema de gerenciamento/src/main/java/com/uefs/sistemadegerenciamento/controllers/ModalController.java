package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.interfaces.ModalCallback;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ModalController {
    private ModalCallback callback;

    @FXML
    private ComboBox serviceListComboBox;

    @FXML
    private Label quantityLabel;

    @FXML
    private TextField quantityField;

    @FXML
    private Button addServiceButton;

    @FXML
    private Label infoLabel;

    @FXML
    private void initialize() {
        quantityField.setVisible(false);
        quantityLabel.setVisible(false);
    }

    // set a function as a callback
    public void setCallback(ModalCallback callback) {
        this.callback = callback;
    }

    public void setServices(List<String> services) {
        serviceListComboBox.getItems().addAll(services);
        serviceListComboBox.setValue(services.get(0));
    }

    public void enableQuantityField() {
        quantityLabel.setVisible(true);
        quantityField.setVisible(true);
        quantityField.setText("1");

    }

    public void setButtonName(String name) {
        addServiceButton.setText(name);
    }

    public void setServiceListComboBox(String[] services) {
        serviceListComboBox.getItems().addAll(services);
    }

    @FXML
    private void onAddServiceButtonClick() {
        quantityField.setStyle("-fx-border-color: #000000");
        serviceListComboBox.setStyle("-fx-border-color: #000000");
        infoLabel.setVisible(false);

        String service = serviceListComboBox.getValue().toString();
        Integer quantity;

        if(quantityField.isVisible()){
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException e) {
                quantityField.setStyle("-fx-border-color: #ff0000");
                info("A quantidade deve ser um número inteiro");
                return;
            }

            if(quantity <= 0) {
                quantityField.setStyle("-fx-border-color: #ff0000");
                info("A quantidade deve ser maior que zero");
                return;
            }
        } else {
            quantity = 1;
        }

        if(service.isEmpty()) {
            serviceListComboBox.setStyle("-fx-border-color: #ff0000");
            info("Selecione um serviço");
            return;
        }

        callback.run(service, quantity);
    }

    public void close(){
        Stage stage = (Stage) addServiceButton.getScene().getWindow();
        stage.close();
    }

    public void info(String message) {
        infoLabel.setVisible(true);
        infoLabel.setStyle("-fx-text-fill: #000000");
        infoLabel.setText(message);
    }

    public void error(String message) {
        infoLabel.setVisible(true);
        infoLabel.setStyle("-fx-text-fill: #ff0000");
        infoLabel.setText(message);
    }

    public void success(String message) {
        infoLabel.setVisible(true);
        infoLabel.setStyle("-fx-text-fill: #157315");
        infoLabel.setText(message);
    }
}
