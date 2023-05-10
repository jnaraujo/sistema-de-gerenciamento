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
import java.util.ArrayList;
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
        List<Button> buttons = new ArrayList<>();

        if(user.getUserType() == UserType.RECEPTIONIST) {
            buttons.addAll(getReceptionistButtons());
        } else if(user.getUserType() == UserType.TECHNICIAN) {
            buttons.addAll(getTechnicianButtons());
        } else if(user.getUserType() == UserType.ADMINISTRATOR) {
            buttons.addAll(getAdministratorButtons());
            buttons.addAll(getTechnicianButtons());
            buttons.addAll(getReceptionistButtons());
        }

        flowPane.getChildren().addAll(buttons);
    }

    private List<Button> getTechnicianButtons(){
        List<Button> buttons = new ArrayList<>();

        Button button = createButton("Listar ordens de serviço");
        button.setOnAction(event -> {
            WorkOrderListController controller = (WorkOrderListController) PageLoader.openPage("work_order_list.fxml");
            controller.setUser(user);

        });
        buttons.add(button);

        button = createButton("Ver estoque");
        button.setOnAction(event -> {
//            InventoryController controller = (InventoryController) PageLoader.openPage("inventory.fxml");
//            controller.setUser(user);
        });
        buttons.add(button);

        button = createButton("Criar ordem de serviço");
        button.setOnAction(event -> {
//            CreateOrderController controller = (CreateOrderController) PageLoader.openPage("create_order.fxml");
//            controller.setUser(user);
        });

        return buttons;
    }

    private List<Button> getReceptionistButtons(){
        List<Button> buttons = new ArrayList<>();

        Button button = createButton("Criar ordem de serviço");
        button.setOnAction(event -> {
//            CreateOrderController controller = (CreateOrderController) PageLoader.openPage("create_order.fxml");
//            controller.setUser(user);
        });
        buttons.add(button);

        return buttons;
    }

    private List<Button> getAdministratorButtons(){
        List<Button> buttons = new ArrayList<>();

        Button button = createButton("Gerar relatório");
        button.setOnAction(event -> {
//            CreateOrderController controller = (CreateOrderController) PageLoader.openPage("create_order.fxml");
//            controller.setUser(user);
        });
        buttons.add(button);

        button = createButton("Gerenciar usuários");
        button.setOnAction(event -> {
//            ManageUsersController controller = (ManageUsersController) PageLoader.openPage("manage_users.fxml");
//            controller.setUser(user);
        });

        return buttons;
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

    private void openPage(String url) {


    }
}
