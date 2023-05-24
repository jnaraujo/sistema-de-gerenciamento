package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.view.components.EmptyComponent;
import com.uefs.sistemadegerenciamento.view.components.UpdateCustomerComponent;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.List;

public class ManageCustomersController extends Controller {
    private ObservableList<Customer> customers;
    private final int COMPONENT_HEIGHT = 95 + 16;
    @FXML
    private VBox customerListVBox;

    @FXML
    private AnchorPane customerListAnchorPane;

    @Override
    public void setLoggedUser(User user) {
        super.setLoggedUser(user);
        customers.addAll(fetchCustomers());
    }
    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Gerenciar Clientes");

        customers = FXCollections.observableArrayList();

        customerListVBox.getChildren().add(EmptyComponent.create("Nenhum cliente cadastrado."));

        customers.addListener(new ListChangeListener<Customer>() {
            @Override
            public void onChanged(Change<? extends Customer> change) {

                customerListVBox.getChildren().removeIf(node -> {
                    return node.getId().equals("empty-component");
                });

                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Customer customer : change.getAddedSubList()) {
                            addCustomerToLayout(customer);
                        }
                    } else if (change.wasRemoved()) {
                        for (Customer customer : change.getRemoved()) {
                            removeCustomerFromLayout(customer);
                        }
                    }
                }

                if(customerListVBox.getChildren().size() == 0) {
                    customerListVBox.getChildren().add(EmptyComponent.create("Nenhum cliente cadastrado."));
                }

                customerListAnchorPane.setPrefHeight(COMPONENT_HEIGHT * customerListVBox.getChildren().size() + 16);
            }
        });
    }

    private void addCustomerToLayout(Customer customer) {
        customerListVBox.getChildren().add(UpdateCustomerComponent.create(customer, (event) -> {
            onUpdateCustomerButtonClick(customer);
        }, (event) -> {
            onDeleteCustomerButtonClick(customer);
        }));
    }

    private void removeCustomerFromLayout(Customer customer) {
        customerListVBox.getChildren().removeIf(node -> {
            return node.getId().equals("customer-" + customer.getId());
        });
    }

    private void onUpdateCustomerButtonClick(Customer updatedCustomer) {
        UpdateCustomerController controller = PageLoader.openPage("update_customer.fxml");
        controller.setLoggedUser(getLoggedUser());
        controller.setUpdatedCustomer(updatedCustomer);
        controller.setPreviousPage("manage_customers.fxml");
    }

    private void onDeleteCustomerButtonClick(Customer deletedCustomer) {
        if(getLoggedUser().getUserType() != UserType.ADMINISTRATOR) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Você não tem permissão para deletar usuários");
            alert.setContentText("Apenas administradores podem deletar usuários");
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
                alert.close();
            });
            alert.showAndWait();
            return;
        }

        List<WorkOrder> customerWorkOrders = DAOManager.getWorkOrderDao().findAllWorkOrdersByCustomer(deletedCustomer.getId());
        if(customerWorkOrders.size() > 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Não é possível deletar o cliente " + deletedCustomer.getName());
            alert.setContentText("Existem ordens de serviço associadas a esse cliente.");
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
                alert.close();
            });
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletar Cliente.");
        alert.setHeaderText("Você tem certeza que deseja deletar o cliente " + deletedCustomer.getName() + "?");
        alert.setContentText("Todas as ordens associadas a esse cliente serão deletadas. A ação não pode ser desfeita.");
        alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
            alert.close();
        });

        ButtonType buttonTypeYes = new ButtonType("Sim, deletar usuário");
        ButtonType buttonTypeNo = new ButtonType("Não");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent((buttonType) -> {
            if(buttonType == buttonTypeYes) {
                deleteCustomer(deletedCustomer);
            }

            alert.close();
        });
    }

    private void deleteCustomer(Customer customer){
        customers.remove(customer);
        DAOManager.getCustomerDao().delete(customer.getId());
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
}
