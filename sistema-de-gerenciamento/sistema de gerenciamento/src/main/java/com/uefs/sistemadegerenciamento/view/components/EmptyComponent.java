package com.uefs.sistemadegerenciamento.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EmptyComponent {
    public static HBox create(String description) {
        final int WIDTH = 1200;

        HBox hBox = new HBox();
        hBox.setId("empty-component");
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(80);
        hBox.setPadding(new javafx.geometry.Insets(8, 8, 8, 8));
        hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #d4d4d4; -fx-border-width: 1px; -fx-border-radius: 8px;");
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.prefWidth(WIDTH);
        vBox.setAlignment(Pos.CENTER);

        vBox.setPadding(new javafx.geometry.Insets(0, 8, 0, 0));

        Label descriptionLabel = new Label(description);
        descriptionLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 16));
        descriptionLabel.setMaxWidth(WIDTH);

        vBox.getChildren().addAll(descriptionLabel);

        hBox.getChildren().addAll(vBox);
        return hBox;
    }
}
