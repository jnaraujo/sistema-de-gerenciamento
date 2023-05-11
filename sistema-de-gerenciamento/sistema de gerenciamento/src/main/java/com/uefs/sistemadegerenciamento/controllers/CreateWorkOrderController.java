package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class CreateWorkOrderController {
    User user;

    @FXML
    public ComboBox customersComboBox;

    private List<Customer> customers;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        System.out.println("initialize");
        HelloApplication.stage.setTitle("Criar Ordem de Serviço");

        customers = DAOManager.getCustomerDao().getAll();
        customers.sort(Comparator.comparing(Customer::getName));


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

    @FXML
    public void onBackButtonClick() {
        PageLoader.goHome(user);
    }
}
