package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.view.components.EmptyWorkOrderComponent;
import com.uefs.sistemadegerenciamento.view.components.WorkOrderComponent;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.List;

public class WorkOrderListController {

    private User loggedUser;

    private ObservableList<WorkOrder> openWorkOrders;

    @FXML
    private VBox workOrderListVBox;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox technicianCurrentWorkOrderVBox;

    @FXML
    private ComboBox filterComboBox;

    private final int COMPONENT_HEIGHT = 83 + 16;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;

        openWorkOrders.addAll(fetchOpenWorkOrders());

        WorkOrder loggedUserWorkOrder = DAOManager.getWorkOrderDao().findOpenOrderByTechnicianId(loggedUser.getId());

        technicianCurrentWorkOrderVBox.getChildren().clear();

        if(loggedUserWorkOrder != null) {
            technicianCurrentWorkOrderVBox.getChildren().add(createWorkOrderComponent(loggedUserWorkOrder, false));
        }else{
            technicianCurrentWorkOrderVBox.getChildren().add(EmptyWorkOrderComponent.create());
        }
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Lista de ordens de serviço");

        openWorkOrders = FXCollections.observableArrayList();

        filterComboBox.getItems().addAll("Todas", "Abertas", "Fechadas");
        filterComboBox.getSelectionModel().select(1);

        workOrderListVBox.getChildren().add(EmptyWorkOrderComponent.create());
        openWorkOrders.addListener(new ListChangeListener<WorkOrder>() {
            @Override
            public void onChanged(Change<? extends WorkOrder> change) {
                WorkOrder loggedUserHaveWorkOrder = DAOManager.getWorkOrderDao().findOpenOrderByTechnicianId(loggedUser.getId());
                boolean doesLoggedUserHaveWorkOrder = loggedUserHaveWorkOrder != null;
                boolean isButtonDisabled = doesLoggedUserHaveWorkOrder;

                technicianCurrentWorkOrderVBox.getChildren().clear();

                if(doesLoggedUserHaveWorkOrder) {
                    technicianCurrentWorkOrderVBox.getChildren().add(createWorkOrderComponent(loggedUserHaveWorkOrder, false));
                }else{
                    technicianCurrentWorkOrderVBox.getChildren().add(EmptyWorkOrderComponent.create());
                }

                workOrderListVBox.getChildren().removeIf(node -> {
                    return node.getId().equals("empty-work-order");
                });

                while (change.next()) {
                    if (change.wasAdded()) {
                        for (WorkOrder workOrder : change.getAddedSubList()) {
                            addWorkOrderToLayout(workOrder, isButtonDisabled);
                        }
                    }

                    if (change.wasRemoved()) {
                        for (WorkOrder workOrder : change.getRemoved()) {
                            removeWorkOrderFromLayout(workOrder);
                        }
                    }

                    if(change.wasUpdated()){
                        for (WorkOrder workOrder : change.getList()) {
                            removeWorkOrderFromLayout(workOrder);
                            addWorkOrderToLayout(workOrder, isButtonDisabled);
                        }
                    }
                }

                if(openWorkOrders.isEmpty()) {
                    workOrderListVBox.getChildren().add(EmptyWorkOrderComponent.create());
                }

                anchorPane.setPrefHeight(COMPONENT_HEIGHT * workOrderListVBox.getChildren().size() + 16);
            }
        });
    }

    private void addWorkOrderToLayout(WorkOrder workOrder, boolean isButtonDisabled) {
        workOrderListVBox.getChildren().add(createWorkOrderComponent(workOrder, isButtonDisabled));
    }

    private void removeWorkOrderFromLayout(WorkOrder workOrder) {
        workOrderListVBox.getChildren().removeIf(node -> {
            return node.getId().equals("work-order-" + workOrder.getId());
        });
    }

    @FXML
    private void onFilterComboBoxAction() {
        String filter = filterComboBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(filter);
        if(filter.equals("Todas")) {
            openWorkOrders.removeAll(openWorkOrders);
            openWorkOrders.addAll(fetchAllWorkOrders());
        }else if(filter.equals("Abertas")) {
            openWorkOrders.removeAll(openWorkOrders);
            openWorkOrders.addAll(fetchOpenWorkOrders());
        }else if(filter.equals("Fechadas")) {
            openWorkOrders.removeAll(openWorkOrders);
            openWorkOrders.addAll(fetchClosedWorkOrders());
        }
    }

    @FXML
    private void onBackButtonClick() {
        PageLoader.goHome(loggedUser);
    }

    private HBox createWorkOrderComponent(WorkOrder workOrder, boolean isButtonDisabled) {
        Customer customer = DAOManager.getCustomerDao().findById(workOrder.getCustomerId());

        return WorkOrderComponent.create(workOrder, customer, loggedUser, isButtonDisabled, (event) -> {
            if(isButtonDisabled){
                System.out.println("Tecnico ja possui uma ordem de serviço");
            }else{
                workOrder.setTechnicianId(loggedUser.getId());
                DAOManager.getWorkOrderDao().update(workOrder);

                openWorkOrders.removeAll(openWorkOrders);
                openWorkOrders.addAll(fetchOpenWorkOrders());
            }
        });
    }

    private List<WorkOrder> fetchOpenWorkOrders() {
        return DAOManager.getWorkOrderDao().findOpenWorkOrders();
    }

    private List<WorkOrder> fetchClosedWorkOrders() {
        List<WorkOrder> workOrders = DAOManager.getWorkOrderDao().getAll();

        workOrders.removeIf(workOrder -> {
            return workOrder.getStatus().equals(OrderStatus.OPEN);
        });

        workOrders.sort(Comparator.comparing(WorkOrder::getCreatedAt));
        return workOrders;
    }

    private List<WorkOrder> fetchAllWorkOrders() {
        List<WorkOrder> workOrders = DAOManager.getWorkOrderDao().getAll();
        workOrders.sort(Comparator.comparing(WorkOrder::getCreatedAt));
        return workOrders;
    }
}
