package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class WorkOrderListController {

    private User loggedUser;

    private ObservableList<WorkOrder> openWorkOrders;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public VBox workOrderListVBox;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public VBox technicianCurrentWorkOrderVBox;

    private final int COMPONENT_HEIGHT = 80 + 16;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;

        openWorkOrders.addAll(fetchWorkOrders());

        WorkOrder loggedUserWorkOrder = DAOManager.getWorkOrderDao().findOrderByTechnicianId(loggedUser.getId());

        technicianCurrentWorkOrderVBox.getChildren().clear();

        if(loggedUserWorkOrder != null) {
            technicianCurrentWorkOrderVBox.getChildren().add(createWorkOrderComponent(loggedUserWorkOrder, false));
        }else{
            technicianCurrentWorkOrderVBox.getChildren().add(EmptyWorkOrderComponent.create());
        }
    }

    private List<WorkOrder> fetchWorkOrders() {
        return DAOManager.getWorkOrderDao().findOpenWorkOrders();
    }

    @FXML
    public void initialize() {
        HelloApplication.stage.setTitle("Lista de ordens de serviço");

        openWorkOrders = FXCollections.observableArrayList();

        workOrderListVBox.getChildren().add(EmptyWorkOrderComponent.create());
        openWorkOrders.addListener(new ListChangeListener<WorkOrder>() {
            @Override
            public void onChanged(Change<? extends WorkOrder> change) {
                WorkOrder loggedUserHaveWorkOrder = DAOManager.getWorkOrderDao().findOrderByTechnicianId(loggedUser.getId());
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
    public void onBackButtonClick() {
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
                openWorkOrders.addAll(fetchWorkOrders());
            }
        });
    }
}
