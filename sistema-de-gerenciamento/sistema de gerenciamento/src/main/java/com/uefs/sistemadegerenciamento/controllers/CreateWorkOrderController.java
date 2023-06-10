package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.WorkOrderManagerApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.utils.converter.CustomerConverter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.Comparator;
import java.util.List;

public class CreateWorkOrderController extends Controller {

    @FXML
    private ComboBox customersComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label infoLabel;

    private List<Customer> customers;

    @FXML
    private void initialize() {
        WorkOrderManagerApplication.stage.setTitle("Criar Ordem de Serviço");

        customers = fetchCustomers();

        customersComboBox.setEditable(true);
        customersComboBox.getItems().addAll(customers);
        customersComboBox.setPromptText("Selecione um cliente");
        customersComboBox.setConverter(new CustomerConverter(customers));


        customersComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
           Platform.runLater(() -> {
               List<Customer> filteredCustomers = customers.stream()
                       .filter(customer ->
                               customer.getName().toLowerCase().contains(newValue.toLowerCase()) || customer.getEmail().toLowerCase().contains(newValue.toLowerCase())
                       )
                       .sorted(Comparator.comparing(Customer::getName))
                       .toList();

                if (filteredCustomers.size() > 0) {
                    customersComboBox.getItems().setAll(filteredCustomers.toArray());
                    customersComboBox.show();
                }
           });
        });
    }

    private List<Customer> fetchCustomers() {
        List<Customer> customers = DAOManager.getCustomerDao().getAll();
        customers.sort(Comparator.comparing(Customer::getName));
        return customers;
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }

    @FXML
    private void onCreateCustomerButtonClick() {
        Controller controller = PageLoader.openPage("create_customer.fxml");
        controller.setLoggedUser(getLoggedUser());
        controller.setPreviousPage("create_order.fxml");
    }

    @FXML
    private void onCreateOrderButtonClick() {
        Customer customer = (Customer) customersComboBox.getValue();

        customersComboBox.getStyleClass().remove("error");;
        descriptionTextArea.getStyleClass().remove("error");;
        info("");

        if(customer == null) {
            customersComboBox.getStyleClass().add("error");
            error("Selecione um cliente.");
            return;
        }

        if(descriptionTextArea.getText().isEmpty()) {
            descriptionTextArea.getStyleClass().add("error");
            error("Digite uma descrição.");
            return;
        }

        WorkOrder workOrder = new WorkOrder(
                descriptionTextArea.getText(),
                customer.getId()
        );

        DAOManager.getWorkOrderDao().save(workOrder);
        success("Ordem de serviço criada com sucesso!");

        descriptionTextArea.setText("");
        customersComboBox.setValue(null);
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
