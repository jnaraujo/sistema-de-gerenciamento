package com.uefs.sistemadegerenciamento.view.components;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class BigButtonComponent {
    public static Button create(String text){
        Font font = new Font("System", 14);
        Button button = new Button(text);

        button.setFont(font);

        button.setAlignment(javafx.geometry.Pos.CENTER);
        button.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        button.setMnemonicParsing(false);

        button.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        button.setTextFill(javafx.scene.paint.Color.WHITE);
        button.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        button.wrapTextProperty().setValue(true);

        button.setPrefWidth(200);
        button.setPrefHeight(75);
        button.setStyle("-fx-background-color: #343a40; -fx-background-radius: 8px;");

        return button;
    }
}
