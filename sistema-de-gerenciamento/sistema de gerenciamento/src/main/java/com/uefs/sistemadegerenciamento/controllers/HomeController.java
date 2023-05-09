package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeController {
    private User user;

    @FXML
    public Label titleLabel;

    @FXML
    public Text userRoleText;

    @FXML
    public FlowPane flowPane;

    public void setUser(User user) {
        this.user = user;
        titleLabel.setText("Bem vindo, " + user.getName()+ "!");
        userRoleText.setText("Seu cargo atual: " + userTypeToText(user.getUserType()));

        setUpListOfUserViewsButtons();
    }

    private String userTypeToText(UserType userType) {
        switch (userType) {
            case ADMINISTRATOR:
                return "Administrador";
            case RECEPTIONIST:
                return "Recepcionista";
            case TECHNICIAN:
                return "Técnico";
            default:
                return "Desconhecido";
        }
    }

    private void setUpListOfUserViewsButtons() {
        Map<UserType, List<List<String>>> views = new HashMap<>();

        views.put(UserType.RECEPTIONIST, List.of(
                List.of("create_order.fxml", "Criar ordem de serviço")
        ));

        views.put(UserType.TECHNICIAN, List.of(
                List.of("create_order.fxml", "Criar ordem de serviço"),
                List.of("list_orders.fxml", "Listar ordens de serviço")
        ));

        views.put(UserType.ADMINISTRATOR, List.of(
                List.of("create_order.fxml", "Criar ordem de serviço"),
                List.of("list_orders.fxml", "Listar ordens de serviço"),
                List.of("list_users.fxml", "Listar usuários"),
                List.of("create_user.fxml", "Criar usuário"),
                List.of("inventory.fxml", "Ver estoque")
        ));

        for (List<String> view : views.get(user.getUserType())) {
            String path = view.get(0);
            String title = view.get(1);

            Button button = createButton(title);

            button.setOnAction(event -> {
                openPage(path, title);
            });

            flowPane.getChildren().add(button);
        }
    }

    private Button createButton(String text) {
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

        button.setPrefWidth(250);
        button.setPrefHeight(75);
        button.setStyle("-fx-background-color: #5c677d; -fx-background-radius: 8px;");

        return button;
    }

    private void openPage(String url, String title) {
        try{
            FXMLLoader fxmlLoader = PageLoader.load(url);
            Parent root = fxmlLoader.load();

            HelloApplication.stage.setTitle(title + " - Sistema de Gerenciamento");

            MainController.staticRoot.getChildren().clear();
            MainController.staticRoot.getChildren().add(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
