package com.uefs.sistemadegerenciamento.view.components;

import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class CleaningServiceComponent {
    public static HBox create(CleaningService service, EventHandler onUpdateButtonClick, EventHandler onDeleteButtonClick) {
        HBox hBox = new HBox();
        hBox.setId("component-"+service.getId());
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(80);
        hBox.setPrefWidth(750);
        hBox.setPadding(new javafx.geometry.Insets(8, 8, 8, 8));
        hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #d4d4d4; -fx-border-width: 1px; -fx-border-radius: 8px;");

        final boolean DOES_UPDATE_BUTTON_EXISTS = onUpdateButtonClick != null;

        int LEFT_WIDTH = 1100;

        if(DOES_UPDATE_BUTTON_EXISTS) {
            LEFT_WIDTH -= 125;
        }

        VBox vBox = new VBox();
        vBox.prefWidth(LEFT_WIDTH);
        vBox.setPadding(new javafx.geometry.Insets(0, 8, 0, 0));

        String name = service.getComponents().stream().reduce("", (acc, serviceName) -> {
            return acc + serviceName + ", ";
        });
        name = name.substring(0, name.length() - 2); // remove last comma

        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("subTitle");
        nameLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 18));
        nameLabel.setPrefWidth(LEFT_WIDTH);

        Label pricePerUnitLabel = new Label("Preço: R$ "+ service.getPrice());
        pricePerUnitLabel.setPrefWidth(LEFT_WIDTH);
        pricePerUnitLabel.setFont(new Font(16));

        Label costPerUnitLabel = new Label("Custo: R$ "+ service.getCost());
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

        if(DOES_UPDATE_BUTTON_EXISTS){
            buttonHBox.getChildren().add(updateButton);
        }
        buttonHBox.getChildren().add(deleteButton);

        hBox.getChildren().addAll(vBox, buttonHBox);

        return hBox;
    }
}
