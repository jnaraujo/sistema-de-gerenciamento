package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.service.BuildingService;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.model.service.Service;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.utils.converter.CustomerConverter;
import com.uefs.sistemadegerenciamento.view.components.BuildingServiceComponent;
import com.uefs.sistemadegerenciamento.view.components.CleaningServiceComponent;
import com.uefs.sistemadegerenciamento.view.components.EmptyComponent;
import com.uefs.sistemadegerenciamento.view.components.InstallationServiceComponent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.List;

public class UpdateWorkOrderController extends Controller {
    private WorkOrder workOrder;
    private List<Customer> customers;
    private Customer customer;

    @FXML
    private ComboBox customersComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox statusComboBox;

    @FXML
    private Label infoLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox servicesListVBox;


    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;

        this.customer = DAOManager.getCustomerDao().findById(workOrder.getCustomerId());

        customersComboBox.setValue(customer);
        descriptionTextArea.setText(workOrder.getDescription());
        statusComboBox.setValue(workOrder.getStatus());
        setServicesToScrollPane(workOrder.getServices());
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Atualizar Ordem de Serviço");

        customers = fetchCustomers();

        customersComboBox.setEditable(true);
        customersComboBox.getItems().addAll(customers);
        customersComboBox.setPromptText("Selecione um cliente");
        customersComboBox.setConverter(new CustomerConverter(customers));

        statusComboBox.getItems().addAll(OrderStatus.OPEN, OrderStatus.CLOSED, OrderStatus.CANCELED);


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

    private void setServicesToScrollPane(List<Service> services){
        servicesListVBox.getChildren().clear();

        servicesListVBox.getChildren().removeIf(node -> {
            return node.getId().equals("empty-component");
        });

        if(services.isEmpty()) {
            servicesListVBox.getChildren().add(EmptyComponent.create("Nenhum serviço cadastrado."));
            return;
        }

        for(Service service : services){
            if(service instanceof CleaningService){
                servicesListVBox.getChildren().add(CleaningServiceComponent.create((CleaningService) service, null, event -> {
                    deleteService(workOrder, service);
                }));
            } else if (service instanceof InstallationService){
                servicesListVBox.getChildren().add(InstallationServiceComponent.create((InstallationService) service, null, event -> {
                    deleteService(workOrder, service);
                }));
            } else if (service instanceof BuildingService){
                servicesListVBox.getChildren().add(BuildingServiceComponent.create((BuildingService) service, event -> {
                    deleteService(workOrder, service);
                }));
            }
        }

        final int COMPONENT_HEIGHT = 105;

        anchorPane.setPrefHeight(COMPONENT_HEIGHT * services.size() + 16);
    }

    private void deleteService(WorkOrder order, Service service){
        order.getServices().remove(service);
        setServicesToScrollPane(order.getServices());
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
        CreateCustomerController controller = PageLoader.openPage("create_customer.fxml");
        controller.setLoggedUser(getLoggedUser());
    }

    @FXML
    private void onUpdateButtonClick() {
        if(!workOrder.getTechnicianId().equals(getLoggedUser().getId())){
            error("Você não tem permissão para atualizar esta ordem de serviço.");
            return;
        }

        if(!checkInputs()) return;

        workOrder.setCustomerId(customer.getId());
        workOrder.setDescription(descriptionTextArea.getText());
        workOrder.setStatus(statusComboBox.getValue().toString());

        DAOManager.getWorkOrderDao().update(workOrder);
        success("Ordem de serviço atualizada com sucesso.");
    }

    private boolean checkInputs(){
        customersComboBox.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        descriptionTextArea.setStyle("-fx-border-color: none; -fx-font-size: 14px");
        info("");

        if(customer == null) {
            customersComboBox.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Selecione um cliente.");
            return false;
        }

        if(descriptionTextArea.getText().isEmpty()) {
            descriptionTextArea.setStyle("-fx-border-color: red; -fx-font-size: 14px");
            error("Digite uma descrição.");
            return false;
        }

        if(statusComboBox.getValue() == null) {
            error("Selecione um status.");
            return false;
        }

        return true;
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
