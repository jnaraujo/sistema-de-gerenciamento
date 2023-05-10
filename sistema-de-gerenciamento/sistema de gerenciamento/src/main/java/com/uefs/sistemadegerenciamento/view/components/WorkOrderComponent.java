package com.uefs.sistemadegerenciamento.view.components;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.User;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class WorkOrderComponent {
    public static HBox create(WorkOrder workOrder, Customer customer, User user,  boolean isButtonDisabled, EventHandler onButtonClick) {
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

        button.setOnAction(onButtonClick);

        boolean isTechnicianWorkOrder = workOrder.getTechnicianId() != null && workOrder.getTechnicianId().equals(user.getId());

        if (isTechnicianWorkOrder) {
            button.setText("Abrir ordem");
            button.setStyle("-fx-background-color: rgba(54,140,243,0.53); -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        }

        hBox.getChildren().addAll(vBox, button);
        return hBox;
    }
}
