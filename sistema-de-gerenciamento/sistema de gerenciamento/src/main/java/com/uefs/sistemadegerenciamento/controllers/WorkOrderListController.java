package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
            workOrderListVBox.getChildren().add(createEmptyWorkOrderComponent());
        }

        if(!doesTechnicianHaveWorkOrder){
            technicianCurrentWorkOrderVBox.getChildren().add(createEmptyWorkOrderComponent());
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
        HomeController controller = PageLoader.openPage("home.fxml");
        controller.setUser(user);
    }

    private HBox createWorkOrderComponent(WorkOrder workOrder, boolean isButtonDisabled, boolean isTechnicianWorkOrder) {
        Customer customer = DAOManager.getCustomerDao().findById(workOrder.getCustomerId());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(80);
        hBox.setPrefWidth(750);
        hBox.setPadding(new javafx.geometry.Insets(8, 8, 8, 8));
        hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #d4d4d4; -fx-border-width: 1px; -fx-border-radius: 8px;");

        VBox vBox = new VBox();
        vBox.prefWidth(600);
        vBox.setPadding(new javafx.geometry.Insets(0, 8, 0, 0));

        Label descriptionLabel = new Label(workOrder.getDescription());
        descriptionLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 16));
        descriptionLabel.setMaxWidth(600);

        Label clientText = new Label("Cliente: " + customer.getName() + " - Phone" + customer.getPhone() + " - Email: " + customer.getEmail());
        clientText.setMaxWidth(600);
        clientText.setFont(new Font(14));


        Label otherDataText = new Label("Status: " + workOrder.getStatus() + " - Data de abertura: " + workOrder.getCreatedAt());
        clientText.setMaxWidth(600);
        otherDataText.setFont(new Font(14));

        vBox.getChildren().addAll(descriptionLabel, clientText, otherDataText);

        Button button = new Button("Pegar ordem");
        button.setMnemonicParsing(false);
        button.setPrefHeight(60);
        button.setPrefWidth(150);
        button.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        if(isButtonDisabled){
            button.setDisable(true);
            button.setStyle("-fx-background-color: #d4d4d4; -fx-text-fill: #626262; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        }
        button.setTextAlignment(TextAlignment.CENTER);
        button.setAlignment(Pos.CENTER);

        button.setOnAction(event -> {
            workOrder.setTechnicianId(user.getId());
            DAOManager.getWorkOrderDao().update(workOrder);
            fetchWorkOrders();
        });

        if (isTechnicianWorkOrder) {
            button.setText("Abrir ordem");
            button.setStyle("-fx-background-color: rgba(54,140,243,0.53); -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");

            button.setOnAction(event -> {
            });
        }

        hBox.getChildren().addAll(vBox, button);
        return hBox;
    }

    private HBox createEmptyWorkOrderComponent(){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(80);
        hBox.setPrefWidth(750);
        hBox.setPadding(new javafx.geometry.Insets(8, 8, 8, 8));
        hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #d4d4d4; -fx-border-width: 1px; -fx-border-radius: 8px;");
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.prefWidth(600);
        vBox.setAlignment(Pos.CENTER);

        vBox.setPadding(new javafx.geometry.Insets(0, 8, 0, 0));

        Label descriptionLabel = new Label("Não há ordens de serviço disponíveis");
        descriptionLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 16));
        descriptionLabel.setMaxWidth(750);

        vBox.getChildren().addAll(descriptionLabel);

        hBox.getChildren().addAll(vBox);
        return hBox;
    }
}
