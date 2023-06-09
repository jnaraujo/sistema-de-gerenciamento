package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.WorkOrderManagerApplication;
import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.errors.ServiceOrderWithoutTechnicianException;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.model.service.BuildingService;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.model.service.Service;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.Formatter;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.utils.converter.CustomerConverter;
import com.uefs.sistemadegerenciamento.utils.converter.TechnicianConverter;
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

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class UpdateWorkOrderController extends Controller {
    private WorkOrder workOrder;
    private List<Customer> customers;

    private List<Technician> technicians;
    private Customer customer;

    @FXML
    private ComboBox<Customer> customersComboBox;

    @FXML
    private ComboBox<Technician> technicianComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Label infoLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox servicesListVBox;


    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;

        this.customer = DAOManager.getCustomerDao().findById(workOrder.getCustomerId());

        technicianComboBox.setValue((Technician) DAOManager.getUserDao().findById(workOrder.getTechnicianId()));
        customersComboBox.setValue(customer);
        descriptionTextArea.setText(workOrder.getDescription());
        statusComboBox.setValue(workOrder.getStatus());
        setServicesToScrollPane(workOrder.getServices());
    }

    @FXML
    private void initialize() {
        WorkOrderManagerApplication.stage.setTitle("Atualizar Ordem de Serviço");

        customers = fetchCustomers();
        technicians = fetchTechnicians();

        customersComboBox.setEditable(true);
        customersComboBox.getItems().addAll(customers);
        customersComboBox.setPromptText("Selecione um cliente");
        customersComboBox.setConverter(new CustomerConverter(customers));

        technicianComboBox.setEditable(true);
        technicianComboBox.getItems().addAll(technicians);
        technicianComboBox.setPromptText("Selecione um técnico");
        technicianComboBox.setConverter(new TechnicianConverter(technicians));

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
                    customersComboBox.getItems().setAll(filteredCustomers);
                    customersComboBox.show();
                }
           });
        });

        technicianComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
           Platform.runLater(() -> {
               List<Technician> filteredTechnicians = technicians.stream()
                       .filter(technician ->
                               technician.getName().toLowerCase().contains(newValue.toLowerCase()) || technician.getEmail().toLowerCase().contains(newValue.toLowerCase())
                       )
                       .sorted(Comparator.comparing(Technician::getName))
                       .toList();

                if (filteredTechnicians.size() > 0) {
                    technicianComboBox.getItems().setAll(filteredTechnicians);
                    technicianComboBox.show();
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
                    if(!canUserUpdateOrder(getLoggedUser())){
                        cantUpdateWorkOrderError();
                        return;
                    }
                    deleteService(workOrder, service);
                }));
            } else if (service instanceof InstallationService){
                servicesListVBox.getChildren().add(InstallationServiceComponent.create((InstallationService) service, null, event -> {
                    if(!canUserUpdateOrder(getLoggedUser())){
                        cantUpdateWorkOrderError();
                        return;
                    }
                    deleteService(workOrder, service);
                }));
            } else if (service instanceof BuildingService){
                servicesListVBox.getChildren().add(BuildingServiceComponent.create((BuildingService) service, event -> {
                    if(!canUserUpdateOrder(getLoggedUser())){
                        cantUpdateWorkOrderError();
                        return;
                    }
                    deleteService(workOrder, service);
                }));
            }
        }

        final int COMPONENT_HEIGHT = 110;

        anchorPane.setPrefHeight(COMPONENT_HEIGHT * services.size() + 16);
    }

    private void cantUpdateWorkOrderError(){
        error("Você não tem permissão para adicionar serviços a esta ordem de serviço.");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");
        alert.setContentText("Você não tem permissão para atualizar esta ordem de serviço.");
        alert.showAndWait();
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

            if(service instanceof BuildingService){
                addDeletedComponentsToInventory((BuildingService) service);
            }

            success("Serviço removido com sucesso!");
            return;
        }

        alert.close();
    }

    private void addDeletedComponentsToInventory(BuildingService service){
        service.getUsedComponents().forEach(component -> {
            if(component instanceof ComputerComponent){
                ComputerComponent inventoryComponent = DAOManager.getInventoryDao().findById(((ComputerComponent) component).getId());
                inventoryComponent.setQuantity(inventoryComponent.getQuantity() + component.getQuantity());
                DAOManager.getInventoryDao().update(inventoryComponent);
            }
        });
    }

    private List<Customer> fetchCustomers() {
        List<Customer> customers = DAOManager.getCustomerDao().getAll();
        customers.sort(Comparator.comparing(Customer::getName));
        return customers;
    }

    private List<Technician> fetchTechnicians() {
        List<Technician> technicians = DAOManager.getUserDao().findAllTechnicians();
        technicians.sort(Comparator.comparing(Technician::getName));
        return technicians;
    }

    @FXML
    private void onAddBuildingService(){
        if(!canUserUpdateOrder(getLoggedUser())){
            cantUpdateWorkOrderError();
            return;
        }

        List<ComputerComponent> components = DAOManager.getInventoryDao().findAvailableComponents();

        ModalController controller = openModal("Adicionar nova peça para a Serviço de Montagem");
        HashMap<String, Integer> services = new HashMap<>();
        components.forEach(component -> services.put(componentToString(component), component.getQuantity()));
        controller.setServices(services);
        controller.enableQuantityField();

        controller.setCallback((choice, quantity) -> {
            ComputerComponent inventoryComponent = components.stream()
                    .filter(component1 -> componentToString(component1).equals(choice))
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

    private String componentToString(ComputerComponent component){
        return component.getName() + " (" + Formatter.currency(component.getPricePerUnit()) + " / und.)";
    }

    @FXML
    private void onAddInstallationService(){
        if(!canUserUpdateOrder(getLoggedUser())){
            cantUpdateWorkOrderError();
            return;
        }

        List<InstallationService> installationServices = DAOManager.getInstallationServiceDao().getAll();

        ModalController controller = openModal("Adicionar serviço de instalação");
        HashMap<String, Integer> services = new HashMap<>();
        installationServices.forEach(service -> services.put(installationServiceToString(service), 0));
        controller.setServices(services);

        controller.setCallback((service, quantity) -> {
            InstallationService installationService = installationServices.stream()
                    .filter(installationService1 -> installationServiceToString(installationService1).equals(service))
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

    private String installationServiceToString(InstallationService installationService){
        return installationService.getDescription() + " (" + Formatter.currency(installationService.getPrice()) + ")";
    }

    @FXML
    private void onAddCleaningService(){
        if(!canUserUpdateOrder(getLoggedUser())){
            cantUpdateWorkOrderError();
            return;
        }

        List<CleaningService> cleaningServices = DAOManager.getCleaningServiceDao().getAll();

        ModalController controller = openModal("Adicionar serviço de limpeza");

        HashMap<String, Integer> services = new HashMap<>();
        cleaningServices.forEach(service -> services.put(cleaningComponentsToString(service), 0));
        controller.setServices(services);
        controller.setCallback((serviceName, quantity) -> {

            CleaningService cleaningService = cleaningServices.stream()
                    .filter(service -> cleaningComponentsToString(service).equals(serviceName))
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

    private String cleaningComponentsToString(CleaningService service){
        List<String> components = service.getComponents();
        String componentsString = components.stream().reduce("", (acc, component) -> {
            return acc + component + ", ";
        });

        return componentsString.substring(0, componentsString.length() - 2) + " (" + Formatter.currency(service.getPrice()) + ")";
    }

    private ModalController openModal(String title){
        try{
            FXMLLoader loader = new FXMLLoader(WorkOrderManagerApplication.class.getResource("modal.fxml"));
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
        controller.setPreviousPage("work_order_list.fxml");
        controller.setLoggedUser(getLoggedUser());
    }

    @FXML
    private void onUpdateButtonClick() throws ServiceOrderWithoutTechnicianException {
        if(!canUserUpdateOrder(getLoggedUser())){
            error("Você não tem permissão para atualizar esta ordem de serviço.");
            return;
        }

        if(!checkInputs()) return;

        workOrder.setCustomerId(customer.getId());
        workOrder.setDescription(descriptionTextArea.getText());

        Technician technician = technicianComboBox.getValue();

        boolean didTechnicianChange = !technician.getId().equals(workOrder.getTechnicianId());
        boolean doesTechnicianHaveWorkOrder = DAOManager.getWorkOrderDao().findOpenOrderByTechnicianId(technician.getId()) != null;

        if(didTechnicianChange && doesTechnicianHaveWorkOrder){
            error("Este técnico já possui uma ordem de serviço em aberto.");
            return;
        }

        boolean isLoggedUserTechnician = getLoggedUser().getUserType().equals(UserType.TECHNICIAN);
        boolean isTechnicianEqualsToLoggedUser = technician.getId().equals(getLoggedUser().getId());
        if(didTechnicianChange && isLoggedUserTechnician && !isTechnicianEqualsToLoggedUser){
            error("Você não pode atribuir esta ordem de serviço a outro técnico.");
            return;
        }

        workOrder.setTechnicianId(technician.getId());

        String status = statusComboBox.getValue().toString();

        boolean isStatusChanged = !status.equals(workOrder.getStatus());
        if(isStatusChanged){ // status changed
            if(status.equals(OrderStatus.OPEN)){
                workOrder.setStatus(OrderStatus.OPEN);
                workOrder.setFinishedAt(null);
            } else if(status.equals(OrderStatus.CLOSED)){
                workOrder.finish();
            } else if(status.equals(OrderStatus.CANCELED)){
                workOrder.cancel();
            }
        }

        workOrder.setStatus(statusComboBox.getValue().toString());

        DAOManager.getWorkOrderDao().update(workOrder);
        success("Ordem de serviço atualizada com sucesso.");
    }

    private boolean canUserUpdateOrder(User user){
        boolean hasTechnician = workOrder.getTechnicianId() != null;
        boolean isFromLoggedUser = hasTechnician && workOrder.getTechnicianId().equals(getLoggedUser().getId());
        boolean isLoggedUserTechnician = getLoggedUser().getUserType().equals(UserType.TECHNICIAN);

        if(isLoggedUserTechnician && isFromLoggedUser) return true;
        if(isLoggedUserTechnician && !isFromLoggedUser && !hasTechnician) return true;
        if(user.getUserType().equals(UserType.ADMINISTRATOR)) return true;

        return false;
    }

    private boolean checkInputs(){
        customersComboBox.setStyle("-fx-border-color: inherit; -fx-font-size: 14px");
        descriptionTextArea.setStyle("-fx-border-color: inherit; -fx-font-size: 14px");
        technicianComboBox.setStyle("-fx-border-color: inherit; -fx-font-size: 14px");
        info("");

        if(customer == null) {
            customersComboBox.getStyleClass().add("error");
            error("Selecione um cliente.");
            return false;
        }

        if(technicianComboBox.getValue() == null) {
            technicianComboBox.getStyleClass().add("error");
            error("Selecione um técnico.");
            return false;
        }

        if(descriptionTextArea.getText().isEmpty()) {
            descriptionTextArea.getStyleClass().add("error");
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
