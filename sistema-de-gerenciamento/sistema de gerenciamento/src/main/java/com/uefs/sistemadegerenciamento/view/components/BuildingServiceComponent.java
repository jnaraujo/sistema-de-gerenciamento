package com.uefs.sistemadegerenciamento.view.components;

import com.uefs.sistemadegerenciamento.model.service.BuildingService;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class BuildingServiceComponent {
    public static HBox create(BuildingService service, EventHandler onDeleteButtonClick) {
        HBox hBox = new HBox();
        hBox.setId("component-building");
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(80);
        hBox.setPadding(new javafx.geometry.Insets(8, 8, 8, 8));
        hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #d4d4d4; -fx-border-width: 1px; -fx-border-radius: 8px;");

        int LEFT_WIDTH = 1100;

        VBox vBox = new VBox();
        vBox.prefWidth(LEFT_WIDTH);
        vBox.setPadding(new javafx.geometry.Insets(0, 8, 0, 0));

        Label nameLabel = new Label("Serviço de Montagem");
        nameLabel.getStyleClass().add("subTitle");
        nameLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 18));
        nameLabel.setPrefWidth(LEFT_WIDTH);

        Label pricePerUnitLabel = new Label("Preço: R$ "+ service.getPrice() + " | Custo: R$ " + service.getCost());
        pricePerUnitLabel.setPrefWidth(LEFT_WIDTH);
        pricePerUnitLabel.setFont(new Font(16));

        String components = service.getUsedComponents().stream().reduce("", (acc, component) -> acc + component.getName() + " (" + component.getQuantity() + "), ", (acc, component) -> acc + component);
        components = components.substring(0, components.length() - 2);

        Label componentsLabel = new Label(components);
        componentsLabel.setPrefWidth(LEFT_WIDTH);
        componentsLabel.setFont(new Font(16));

        vBox.getChildren().addAll(nameLabel, componentsLabel, pricePerUnitLabel);

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setPrefHeight(80);
        buttonHBox.setSpacing(8);
        
        Button deleteButton = new Button("Excluir");
        deleteButton.setMnemonicParsing(false);
        deleteButton.setPrefHeight(50);
        deleteButton.setPrefWidth(125);
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        deleteButton.setTextAlignment(TextAlignment.CENTER);
        deleteButton.setAlignment(Pos.CENTER);
        deleteButton.setOnAction(onDeleteButtonClick);

        buttonHBox.getChildren().addAll(deleteButton);

        hBox.getChildren().addAll(vBox, buttonHBox);

        return hBox;
    }
}
