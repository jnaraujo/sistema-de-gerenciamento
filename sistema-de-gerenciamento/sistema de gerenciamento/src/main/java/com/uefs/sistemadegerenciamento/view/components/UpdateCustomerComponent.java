package com.uefs.sistemadegerenciamento.view.components;

import com.uefs.sistemadegerenciamento.model.Customer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class UpdateCustomerComponent {
    public static HBox create(Customer customer, EventHandler onUpdateButtonClick, EventHandler onDeleteButtonClick) {
        HBox hBox = new HBox();
        hBox.setId("customer-"+customer.getId());
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(80);
        hBox.setPrefWidth(750);
        hBox.setPadding(new javafx.geometry.Insets(8, 8, 8, 8));
        hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #d4d4d4; -fx-border-width: 1px; -fx-border-radius: 8px;");

        int LEFT_WIDTH = 500;

        VBox vBox = new VBox();
        vBox.prefWidth(LEFT_WIDTH);
        vBox.setPadding(new javafx.geometry.Insets(0, 8, 0, 0));

        Label nameLabel = new Label(customer.getName());
        nameLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 18));
        nameLabel.setPrefWidth(LEFT_WIDTH);

        Label contactLabel = new Label("Email: "+customer.getEmail() + " | Telefone: "+customer.getPhone());
        contactLabel.setPrefWidth(LEFT_WIDTH);
        contactLabel.setFont(new Font(16));

        Label addressLabel = new Label("Endereço: "+ customer.getAddress());
        addressLabel.setPrefWidth(LEFT_WIDTH);
        addressLabel.setFont(new Font(16));

        vBox.getChildren().addAll(nameLabel, contactLabel, addressLabel);

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setPrefHeight(80);
        buttonHBox.setSpacing(8);

        Button updateCustomerButton = new Button("Editar");
        updateCustomerButton.setMnemonicParsing(false);
        updateCustomerButton.setPrefHeight(50);
        updateCustomerButton.setPrefWidth(125);
        updateCustomerButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        updateCustomerButton.setTextAlignment(TextAlignment.CENTER);
        updateCustomerButton.setAlignment(Pos.CENTER);
        updateCustomerButton.setOnAction(onUpdateButtonClick);
        
        Button deleteCustomerButton = new Button("Excluir");
        deleteCustomerButton.setMnemonicParsing(false);
        deleteCustomerButton.setPrefHeight(50);
        deleteCustomerButton.setPrefWidth(125);
        deleteCustomerButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        deleteCustomerButton.setTextAlignment(TextAlignment.CENTER);
        deleteCustomerButton.setAlignment(Pos.CENTER);
        deleteCustomerButton.setOnAction(onDeleteButtonClick);

        buttonHBox.getChildren().addAll(updateCustomerButton, deleteCustomerButton);

        hBox.getChildren().addAll(vBox, buttonHBox);

        return hBox;
    }
}
