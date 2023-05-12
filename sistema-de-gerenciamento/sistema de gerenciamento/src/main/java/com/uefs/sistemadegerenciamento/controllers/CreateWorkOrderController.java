package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;
import java.util.Comparator;
import java.util.List;

public class CreateWorkOrderController {
    private User loggedUser;

    @FXML
    public ComboBox customersComboBox;

    @FXML
    public TextArea descriptionTextArea;

    @FXML
    public Label infoLabel;

    private List<Customer> customers;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    @FXML
    public void initialize() {
        HelloApplication.stage.setTitle("Criar Ordem de Serviço");

        customers = fetchCustomers();

        customersComboBox.setEditable(true);
        customersComboBox.getItems().addAll(customers);
        customersComboBox.setPromptText("Selecione um cliente");
        customersComboBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                if(customer == null) return "";

                return customer.getName() + " - " + customer.getEmail();
            }

            @Override
            public Customer fromString(String string) {
                return (Customer) customersComboBox.getItems()
                        .stream()
                        .filter(customer -> this.toString((Customer) customer).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });


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
    public void onBackButtonClick() {
        PageLoader.goHome(loggedUser);
    }

    @FXML
    public void onCreateCustomerButtonClick() {
        CreateCustomerController controller = PageLoader.openPage("create_customer.fxml");
        controller.setLoggedUser(loggedUser);
    }

    @FXML
    public void onCreateOrderButtonClick() {
        Customer customer = (Customer) customersComboBox.getValue();

        customersComboBox.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        descriptionTextArea.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        info("");

        if(customer == null) {
            customersComboBox.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Selecione um cliente.");
            return;
        }

        if(descriptionTextArea.getText().isEmpty()) {
            descriptionTextArea.setStyle("-fx-border-color: red; -fx-font-size: 14px");
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
        infoLabel.setStyle("-fx-text-fill: #00ff00");
        infoLabel.setText(message);
    }
}
