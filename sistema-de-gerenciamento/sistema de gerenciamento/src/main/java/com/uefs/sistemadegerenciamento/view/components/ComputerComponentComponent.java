package com.uefs.sistemadegerenciamento.view.components;

import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ComputerComponentComponent {
    public static HBox create(ComputerComponent component, EventHandler onUpdateButtonClick, EventHandler onDeleteButtonClick) {
        HBox hBox = new HBox();
        hBox.setId("component-"+component.getId());
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(80);
        hBox.setPrefWidth(750);
        hBox.setPadding(new javafx.geometry.Insets(8, 8, 8, 8));
        hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #d4d4d4; -fx-border-width: 1px; -fx-border-radius: 8px;");

        int LEFT_WIDTH = 500;

        VBox vBox = new VBox();
        vBox.prefWidth(LEFT_WIDTH);
        vBox.setPadding(new javafx.geometry.Insets(0, 8, 0, 0));

        Label nameLabel = new Label(component.getName() + " ("+component.getManufacturer()+")" + " - "+component.getQuantity()+" unidade(s)");
        nameLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 18));
        nameLabel.setPrefWidth(LEFT_WIDTH);

        Label pricePerUnitLabel = new Label("Preço por unidade: R$ "+ component.getPricePerUnit());
        pricePerUnitLabel.setPrefWidth(LEFT_WIDTH);
        pricePerUnitLabel.setFont(new Font(16));

        Label costPerUnitLabel = new Label("Custo por unidade: R$ "+ component.getCostPerUnit());
        costPerUnitLabel.setPrefWidth(LEFT_WIDTH);
        costPerUnitLabel.setFont(new Font(16));

        vBox.getChildren().addAll(nameLabel, pricePerUnitLabel, costPerUnitLabel);

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setPrefHeight(80);
        buttonHBox.setSpacing(8);

        Button updateButton = new Button("Editar");
        updateButton.setMnemonicParsing(false);
        updateButton.setPrefHeight(50);
        updateButton.setPrefWidth(125);
        updateButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        updateButton.setTextAlignment(TextAlignment.CENTER);
        updateButton.setAlignment(Pos.CENTER);
        updateButton.setOnAction(onUpdateButtonClick);
        
        Button deleteButton = new Button("Excluir");
        deleteButton.setMnemonicParsing(false);
        deleteButton.setPrefHeight(50);
        deleteButton.setPrefWidth(125);
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        deleteButton.setTextAlignment(TextAlignment.CENTER);
        deleteButton.setAlignment(Pos.CENTER);
        deleteButton.setOnAction(onDeleteButtonClick);

        buttonHBox.getChildren().addAll(updateButton, deleteButton);

        hBox.getChildren().addAll(vBox, buttonHBox);

        return hBox;
    }
}