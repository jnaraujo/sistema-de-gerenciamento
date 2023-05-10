package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.view.components.EmptyWorkOrderComponent;
import com.uefs.sistemadegerenciamento.view.components.WorkOrderComponent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class WorkOrderListController {

    private User user;

    private List<WorkOrder> openWorkOrders;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public VBox workOrderListVBox;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public VBox technicianCurrentWorkOrderVBox;

    public void setUser(User user) {
        this.user = user;

        fetchWorkOrders();

        int componentHeight = 80 + 16;
        anchorPane.setPrefHeight(componentHeight * workOrderListVBox.getChildren().size() + 16);
    }

    private void fetchWorkOrders() {
        technicianCurrentWorkOrderVBox.getChildren().clear();
        workOrderListVBox.getChildren().clear();

        List<WorkOrder> workOrders = DAOManager.getWorkOrderDao().findOpenWorkOrders();
        openWorkOrders = workOrders;

        boolean doesTechnicianHaveWorkOrder = DAOManager.getWorkOrderDao().findOrderByTechnicianId(user.getId()) != null;
        boolean isButtonDisabled = doesTechnicianHaveWorkOrder;

        for (WorkOrder workOrder : workOrders) {
            workOrderListVBox.getChildren().add(createWorkOrderComponent(workOrder, isButtonDisabled, false));
        }

        if(workOrders.isEmpty()) {
            workOrderListVBox.getChildren().add(EmptyWorkOrderComponent.create());
        }

        if(!doesTechnicianHaveWorkOrder){
            technicianCurrentWorkOrderVBox.getChildren().add(EmptyWorkOrderComponent.create());
        }

        if (doesTechnicianHaveWorkOrder) {
            WorkOrder workOrder = DAOManager.getWorkOrderDao().findOrderByTechnicianId(user.getId());
            technicianCurrentWorkOrderVBox.getChildren().add(createWorkOrderComponent(workOrder, false, true));
        }
    }

    @FXML
    public void initialize() {
        HelloApplication.stage.setTitle("Lista de ordens de serviço");
    }

    @FXML
    public void onBackButtonClick() {
        PageLoader.goHome(user);
    }

    private HBox createWorkOrderComponent(WorkOrder workOrder, boolean isButtonDisabled, boolean doesTechnicianHaveAWorkOrder) {
        Customer customer = DAOManager.getCustomerDao().findById(workOrder.getCustomerId());

        return WorkOrderComponent.create(workOrder, customer, user, isButtonDisabled, (event) -> {
            if(doesTechnicianHaveAWorkOrder){
                System.out.println("Tecnico ja possui uma ordem de serviço");
            }else{
                workOrder.setTechnicianId(user.getId());
                DAOManager.getWorkOrderDao().update(workOrder);
                fetchWorkOrders();
            }
        });
    }
}
