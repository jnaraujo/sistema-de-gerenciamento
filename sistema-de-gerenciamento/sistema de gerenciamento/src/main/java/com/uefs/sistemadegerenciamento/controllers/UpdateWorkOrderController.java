package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.component.Component;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
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

        final int COMPONENT_HEIGHT = 110;

        anchorPane.setPrefHeight(COMPONENT_HEIGHT * services.size() + 16);
    }

    private void deleteService(WorkOrder order, Service service){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover serviço");
        alert.setHeaderText("Remover serviço");
        alert.setContentText("Deseja realmente remover o serviço?");
        alert.showAndWait();

        if(alert.getResult().getText().equals("OK")){
            order.getServices().remove(service);
            setServicesToScrollPane(order.getServices());
            DAOManager.getWorkOrderDao().update(order);
            success("Serviço removido com sucesso!");
            return;
        }

        alert.close();
    }

    private List<Customer> fetchCustomers() {
        List<Customer> customers = DAOManager.getCustomerDao().getAll();
        customers.sort(Comparator.comparing(Customer::getName));
        return customers;
    }

    @FXML
    private void onAddBuildingService(){
        List<ComputerComponent> components = DAOManager.getInventoryDao().findAvailableComponents();

        ModalController controller = openModal("Adicionar nova peça para a Serviço de Montagem");
        controller.setServices(components.stream().map(component -> component.getName()).toList());
        controller.enableQuantityField();

        controller.setCallback((choice, quantity) -> {
            ComputerComponent inventoryComponent = components.stream()
                    .filter(component1 -> component1.getName().equals(choice))
                    .findFirst()
                    .orElse(null);

            if (inventoryComponent == null) {
                controller.info("Peça não encontrada.");
                return;
            }

            if (inventoryComponent.getQuantity() < quantity) {
                controller.info("Quantidade indisponível.");
                return;
            }

            System.out.println(quantity);

            BuildingService buildingService = new BuildingService();

            ComputerComponent serviceComponent = new ComputerComponent(
                    inventoryComponent.getName(),
                    inventoryComponent.getManufacturer(),
                    inventoryComponent.getPricePerUnit(),
                    inventoryComponent.getCostPerUnit(),
                    quantity
            );
            serviceComponent.setId(inventoryComponent.getId());

            buildingService.addComponent(serviceComponent);
            workOrder.getServices().add(buildingService);

            Integer newQuantity = inventoryComponent.getQuantity() - quantity;
            inventoryComponent.setQuantity(newQuantity);

            DAOManager.getInventoryDao().update(inventoryComponent);
            DAOManager.getWorkOrderDao().update(workOrder);

            setServicesToScrollPane(workOrder.getServices());

            success("Peça adicionada com sucesso!");
            controller.close();
        }
        );
    }

    @FXML
    private void onAddInstallationService(){
        List<InstallationService> installationServices = DAOManager.getInstallationServiceDao().getAll();

        ModalController controller = openModal("Adicionar serviço de instalação");
        controller.setServices(installationServices.stream().map(service -> service.getDescription()).toList());

        controller.setCallback((service, quantity) -> {
            InstallationService installationService = installationServices.stream()
                    .filter(installationService1 -> installationService1.getDescription().equals(service))
                    .findFirst()
                    .orElse(null);

            if(installationService == null) {
                controller.info("Serviço não encontrado.");
                return;
            }

            workOrder.getServices().add(installationService);
            setServicesToScrollPane(workOrder.getServices());
            DAOManager.getWorkOrderDao().update(workOrder);
            success("Serviço adicionado com sucesso!");

            controller.close();
        });
    }

    @FXML
    private void onAddCleaningService(){
        List<CleaningService> cleaningServices = DAOManager.getCleaningServiceDao().getAll();

        ModalController controller = openModal("Adicionar serviço de limpeza");

        List<String> services = cleaningServices.stream().map(service -> cleaningComponentsToString(service.getComponents())).toList();
        controller.setServices(services);
        controller.setCallback((serviceName, quantity) -> {

            CleaningService cleaningService = cleaningServices.stream()
                    .filter(service -> cleaningComponentsToString(service.getComponents()).equals(serviceName))
                    .findFirst()
                    .orElse(null);

            if(cleaningService == null) {
                controller.info("Serviço não encontrado.");
                return;
            }

            workOrder.getServices().add(cleaningService);
            setServicesToScrollPane(workOrder.getServices());
            DAOManager.getWorkOrderDao().update(workOrder);

            success("Serviço adicionado com sucesso!");

            controller.close();
        });
    }

    private String cleaningComponentsToString(List<String> components){
        String componentsString = components.stream().reduce("", (acc, component) -> {
            return acc + component + ", ";
        });

        return componentsString.substring(0, componentsString.length() - 2);
    }

    private ModalController openModal(String title){
        try{
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("modal.fxml"));
            Scene scene = new Scene(loader.load());

            ModalController controller = loader.getController();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(title);
            stage.setWidth(400);
            stage.setHeight(300);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            return controller;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
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
