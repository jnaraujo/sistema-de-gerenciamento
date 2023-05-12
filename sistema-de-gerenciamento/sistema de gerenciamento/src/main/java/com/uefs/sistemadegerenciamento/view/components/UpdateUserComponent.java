package com.uefs.sistemadegerenciamento.view.components;

import com.uefs.sistemadegerenciamento.constants.UserType;
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

public class UpdateUserComponent {
    public static HBox create(User user, boolean isButtonDisabled, EventHandler onUpdateButtonClick, EventHandler onDeleteButtonClick) {
        HBox hBox = new HBox();
        hBox.setId("user-"+user.getId());
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(80);
        hBox.setPrefWidth(750);
        hBox.setPadding(new javafx.geometry.Insets(8, 8, 8, 8));
        hBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #d4d4d4; -fx-border-width: 1px; -fx-border-radius: 8px;");

        int LEFT_WIDTH = 500;

        VBox vBox = new VBox();
        vBox.prefWidth(LEFT_WIDTH);
        vBox.setPadding(new javafx.geometry.Insets(0, 8, 0, 0));

        Label nameLabel = new Label(user.getName());
        nameLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 18));
        nameLabel.setPrefWidth(LEFT_WIDTH);

        Label emailLabel = new Label("Email: "+user.getEmail());
        emailLabel.setPrefWidth(LEFT_WIDTH);
        emailLabel.setFont(new Font(16));

        Label cargoLabel = new Label("Cargo: "+ userTypeToLabel(user.getUserType()));
        cargoLabel.setPrefWidth(LEFT_WIDTH);
        cargoLabel.setFont(new Font(16));

        vBox.getChildren().addAll(nameLabel, emailLabel, cargoLabel);

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setPrefHeight(80);
        buttonHBox.setSpacing(8);

        Button updateUserButton = new Button("Editar");
        updateUserButton.setMnemonicParsing(false);
        updateUserButton.setPrefHeight(50);
        updateUserButton.setPrefWidth(125);
        updateUserButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        updateUserButton.setTextAlignment(TextAlignment.CENTER);
        updateUserButton.setAlignment(Pos.CENTER);
        updateUserButton.setOnAction(onUpdateButtonClick);
        
        Button deleteUserButton = new Button("Excluir");
        deleteUserButton.setMnemonicParsing(false);
        deleteUserButton.setPrefHeight(50);
        deleteUserButton.setPrefWidth(125);
        deleteUserButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: #fff; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        deleteUserButton.setTextAlignment(TextAlignment.CENTER);
        deleteUserButton.setAlignment(Pos.CENTER);
        deleteUserButton.setOnAction(onDeleteButtonClick);
        
        if(isButtonDisabled){
            updateUserButton.setDisable(true);
            deleteUserButton.setDisable(true);
            updateUserButton.setStyle("-fx-background-color: #d4d4d4; -fx-text-fill: #626262; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
            deleteUserButton.setStyle("-fx-background-color: #d4d4d4; -fx-text-fill: #626262; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 16px;");
        }

        buttonHBox.getChildren().addAll(updateUserButton, deleteUserButton);

        hBox.getChildren().addAll(vBox, buttonHBox);

        return hBox;
    }
    
    static private String userTypeToLabel(UserType type){
        return switch (type) {
            case ADMINISTRATOR -> "Administrador";
            case RECEPTIONIST -> "Recepcionista";
            case TECHNICIAN -> "Técnico";
        };
    }
}
