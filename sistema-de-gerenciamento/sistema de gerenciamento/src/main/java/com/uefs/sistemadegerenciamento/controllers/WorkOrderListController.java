package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.view.components.EmptyComponent;
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

    private final int COMPONENT_HEIGHT = 105 + 16;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;

        openWorkOrders.addAll(fetchOpenWorkOrders());

        WorkOrder loggedUserWorkOrder = DAOManager.getWorkOrderDao().findOpenOrderByTechnicianId(loggedUser.getId());

        technicianCurrentWorkOrderVBox.getChildren().clear();

        if(loggedUserWorkOrder != null) {
            technicianCurrentWorkOrderVBox.getChildren().add(createWorkOrderComponent(loggedUserWorkOrder, (Technician) loggedUser, false));
        }else{
            technicianCurrentWorkOrderVBox.getChildren().add(EmptyComponent.create("Não há ordens de serviço disponíveis"));
        }
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Lista de ordens de serviço");

        openWorkOrders = FXCollections.observableArrayList();

        filterComboBox.getItems().addAll("Todas", "Abertas", "Fechadas");
        filterComboBox.getSelectionModel().select(1);

        workOrderListVBox.getChildren().add(EmptyComponent.create("Não há ordens de serviço disponíveis"));
        openWorkOrders.addListener(new ListChangeListener<WorkOrder>() {
            @Override
            public void onChanged(Change<? extends WorkOrder> change) {
                WorkOrder loggedUserWorkOrder = DAOManager.getWorkOrderDao().findOpenOrderByTechnicianId(loggedUser.getId());
                boolean doesLoggedUserHaveWorkOrder = loggedUserWorkOrder != null;

                technicianCurrentWorkOrderVBox.getChildren().clear();

                if(doesLoggedUserHaveWorkOrder) {
                    technicianCurrentWorkOrderVBox.getChildren().add(createWorkOrderComponent(loggedUserWorkOrder, (Technician) loggedUser, false));
                }else{
                    technicianCurrentWorkOrderVBox.getChildren().add(EmptyComponent.create("Não há ordens de serviço disponíveis"));
                }

                workOrderListVBox.getChildren().removeIf(node -> {
                    return node.getId().equals("empty-component");
                });

                while (change.next()) {
                    if (change.wasAdded()) {
                        for (WorkOrder workOrder : change.getAddedSubList()) {
                            boolean doesWorkOrderHaveTechnician = workOrder.getTechnicianId() != null;
                            boolean isWorkOrderClosed = workOrder.getStatus() == OrderStatus.CLOSED;

                            boolean isButtonDisabled = doesLoggedUserHaveWorkOrder || isWorkOrderClosed || doesWorkOrderHaveTechnician;

                            if(!doesWorkOrderHaveTechnician){
                                addWorkOrderToLayout(workOrder, null, isButtonDisabled);
                            }else{
                                Technician technician = (Technician) DAOManager.getUserDao().findById(workOrder.getTechnicianId());
                                addWorkOrderToLayout(workOrder, technician, true);
                            }
                        }
                    }

                    if (change.wasRemoved()) {
                        for (WorkOrder workOrder : change.getRemoved()) {
                            removeWorkOrderFromLayout(workOrder);
                        }
                    }
                }

                if(openWorkOrders.isEmpty()) {
                    workOrderListVBox.getChildren().add(EmptyComponent.create("Não há ordens de serviço disponíveis"));
                }

                anchorPane.setPrefHeight(COMPONENT_HEIGHT * workOrderListVBox.getChildren().size() + 16);
            }
        });
    }

    private void addWorkOrderToLayout(WorkOrder workOrder, Technician technician, boolean isButtonDisabled) {
        workOrderListVBox.getChildren().add(createWorkOrderComponent(workOrder, technician, isButtonDisabled));
    }

    private void removeWorkOrderFromLayout(WorkOrder workOrder) {
        workOrderListVBox.getChildren().removeIf(node -> {
            return node.getId().equals("work-order-" + workOrder.getId());
        });
    }

    @FXML
    private void onFilterComboBoxAction() {
        String filter = filterComboBox.getSelectionModel().getSelectedItem().toString();
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

    private HBox createWorkOrderComponent(WorkOrder workOrder, Technician technician, boolean isButtonDisabled) {
        Customer customer = DAOManager.getCustomerDao().findById(workOrder.getCustomerId());

        return WorkOrderComponent.create(workOrder, technician, customer, loggedUser, isButtonDisabled, (event) -> {
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
