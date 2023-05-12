package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.view.components.BigButtonComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

public class HomeController {
    private User loggedUser;

    @FXML
    private Label titleLabel;

    @FXML
    private Text userRoleText;

    @FXML
    private FlowPane flowPane;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
        titleLabel.setText("Bem vindo, " + loggedUser.getName()+ "!");
        userRoleText.setText("Seu cargo atual: " + userTypeToText(loggedUser.getUserType()));

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

        if(loggedUser.getUserType() == UserType.RECEPTIONIST) {
            buttons.addAll(getReceptionistButtons());
        } else if(loggedUser.getUserType() == UserType.TECHNICIAN) {
            buttons.addAll(getTechnicianButtons());
        } else if(loggedUser.getUserType() == UserType.ADMINISTRATOR) {
            buttons.addAll(getAdministratorButtons());
            buttons.addAll(getTechnicianButtons());
            buttons.addAll(getReceptionistButtons());
        }

        flowPane.getChildren().addAll(buttons);
    }

    private List<Button> getTechnicianButtons(){
        List<Button> buttons = new ArrayList<>();

        Button button = BigButtonComponent.create("\uD83D\uDCCB Listar ordens de serviço");
        button.setOnAction(event -> {
            WorkOrderListController controller = PageLoader.openPage("work_order_list.fxml");
            controller.setLoggedUser(loggedUser);

        });
        buttons.add(button);

        button = BigButtonComponent.create("\uD83D\uDCE6 Ver estoque");
        button.setOnAction(event -> {
//            InventoryController controller = (InventoryController) PageLoader.openPage("inventory.fxml");
//            controller.setUser(user);
        });
        buttons.add(button);

        return buttons;
    }

    private List<Button> getReceptionistButtons(){
        List<Button> buttons = new ArrayList<>();

        Button button = BigButtonComponent.create("\uD83D\uDD27 Criar ordem de serviço");
        button.setOnAction(event -> {
            CreateWorkOrderController controller = PageLoader.openPage("create_order.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.add(button);

        button = BigButtonComponent.create("\uD83E\uDDD1\u200D\uD83D\uDCBC Criar novo Cliente");
        button.setOnAction(event -> {
            CreateCustomerController controller = PageLoader.openPage("create_customer.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.add(button);

        return buttons;
    }

    private List<Button> getAdministratorButtons(){
        List<Button> buttons = new ArrayList<>();

        Button button = BigButtonComponent.create("\uD83D\uDCCA Gerar relatório");
        button.setOnAction(event -> {
//            CreateOrderController controller = (CreateOrderController) PageLoader.openPage("create_order.fxml");
//            controller.setUser(user);
        });
        buttons.add(button);

        button = BigButtonComponent.create("\uD83D\uDC64 Criar novo usuário");
        button.setOnAction(event -> {
            CreateUserController controller = (CreateUserController) PageLoader.openPage("create_user.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.add(button);

        button = BigButtonComponent.create("\uD83D\uDC65 Gerenciar usuários");
        button.setOnAction(event -> {
            ManageUserController controller = PageLoader.openPage("manage_user.fxml");
            controller.setUser(loggedUser);
        });
        buttons.add(button);

        return buttons;
    }

    public void onLogOutClick() {
        PageLoader.openPage("login.fxml");
    }
}
