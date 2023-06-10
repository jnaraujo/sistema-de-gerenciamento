package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.utils.UserTypeParser;
import com.uefs.sistemadegerenciamento.view.components.BigButtonComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeController extends Controller {
    private User loggedUser;

    @FXML
    private Label titleLabel;

    @FXML
    private Text userRoleText;

    @FXML
    private FlowPane workOrderFlowPane;

    @FXML
    private FlowPane customerFlowPane;

    @FXML
    private FlowPane userFlowPane;

    @FXML
    private FlowPane serviceFlowPane;

    @FXML
    private FlowPane otherFlowPane;

    @FXML
    private VBox workOrderVBox;

    @FXML
    private VBox customerVBox;

    @FXML
    private VBox userVBox;

    @FXML
    private VBox serviceVBox;

    @FXML
    private VBox mainVBox;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
        titleLabel.setText("Bem vindo, " + loggedUser.getName()+ "!");
        userRoleText.setText("Seu cargo atual: " + UserTypeParser.toString(loggedUser.getUserType()));

        setUpListOfUserViewsButtons();
    }

    private void setUpListOfUserViewsButtons() {
        HashMap<String, List<Button>> buttons = new HashMap<>();
        buttons.put("workOrderButtons", new ArrayList<>());
        buttons.put("customerButtons", new ArrayList<>());
        buttons.put("userButtons", new ArrayList<>());
        buttons.put("serviceButtons", new ArrayList<>());

        if(loggedUser.getUserType() == UserType.RECEPTIONIST) {
            HashMap<String, List<Button>> receptionistButtons = getReceptionistButtons();
            for(String key : receptionistButtons.keySet()) {
                buttons.get(key).addAll(receptionistButtons.get(key));
            }
        } else if(loggedUser.getUserType() == UserType.TECHNICIAN) {
            HashMap<String, List<Button>> technicianButtons = getTechnicianButtons();
            for(String key : technicianButtons.keySet()) {
                buttons.get(key).addAll(technicianButtons.get(key));
            }
        } else if(loggedUser.getUserType() == UserType.ADMINISTRATOR) {
            HashMap<String, List<Button>> administratorButtons = getAdministratorButtons();
            for(String key : administratorButtons.keySet()) {
                buttons.get(key).addAll(administratorButtons.get(key));
            }
            HashMap<String, List<Button>> technicianButtons = getTechnicianButtons();
            for(String key : technicianButtons.keySet()) {
                buttons.get(key).addAll(technicianButtons.get(key));
            }
            HashMap<String, List<Button>> receptionistButtons = getReceptionistButtons();
            for(String key : receptionistButtons.keySet()) {
                buttons.get(key).addAll(receptionistButtons.get(key));
            }
        }

        if(buttons.get("workOrderButtons").size() > 0) {
            workOrderFlowPane.getChildren().addAll(buttons.get("workOrderButtons"));
        } else {
            mainVBox.getChildren().remove(workOrderVBox);
        }

        if(buttons.get("customerButtons").size() > 0) {
            customerFlowPane.getChildren().addAll(buttons.get("customerButtons"));
        } else {
            mainVBox.getChildren().remove(customerVBox);
        }

        if(buttons.get("userButtons").size() > 0) {
            userFlowPane.getChildren().addAll(buttons.get("userButtons"));
        } else {
            mainVBox.getChildren().remove(userVBox);
        }

        if(buttons.get("serviceButtons").size() > 0) {
            serviceFlowPane.getChildren().addAll(buttons.get("serviceButtons"));
        } else {
            mainVBox.getChildren().remove(serviceVBox);
        }
    }

    private HashMap<String, List<Button>> getTechnicianButtons(){
        HashMap<String, List<Button>> buttons = new HashMap<>();
        buttons.put("workOrderButtons", new ArrayList<>());
        buttons.put("serviceButtons", new ArrayList<>());

        Button button = BigButtonComponent.create("\uD83D\uDCCB Listar ordens de serviço");
        button.setOnAction(event -> {
            WorkOrderListController controller = PageLoader.openPage("work_order_list.fxml");
            controller.setLoggedUser(loggedUser);

        });
        buttons.get("workOrderButtons").add(button);

        button = BigButtonComponent.create("\uD83D\uDCE6 Gerenciar estoque");
        button.setOnAction(event -> {
            ManageInventoryController controller = PageLoader.openPage("manage_inventory.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.get("workOrderButtons").add(button);

        button = BigButtonComponent.create("\uD83D\uDEE0 Gerenciar Serviços de Instalação");
        button.setOnAction(event -> {
            ManageInstallationServiceController controller = PageLoader.openPage("manage_installation_service.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.get("serviceButtons").add(button);

        button = BigButtonComponent.create("\uD83E\uDDF9 Gerenciar Serviços de Limpeza");
        button.setOnAction(event -> {
            ManageCleaningServiceController controller = PageLoader.openPage("manage_cleaning_service.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.get("serviceButtons").add(button);

        return buttons;
    }

    private HashMap<String, List<Button>> getReceptionistButtons(){
        HashMap<String, List<Button>> buttons = new HashMap<>();
        buttons.put("workOrderButtons", new ArrayList<>());
        buttons.put("customerButtons", new ArrayList<>());

        Button button = BigButtonComponent.create("\uD83D\uDD27 Criar ordem de serviço");
        button.setOnAction(event -> {
            CreateWorkOrderController controller = PageLoader.openPage("create_order.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.get("workOrderButtons").add(button);

        button = BigButtonComponent.create("\uD83D\uDCCB Listar ordens de serviço");
        button.setOnAction(event -> {
            WorkOrderListController controller = PageLoader.openPage("work_order_list.fxml");
            controller.setLoggedUser(loggedUser);

        });
        buttons.get("workOrderButtons").add(button);

        button = BigButtonComponent.create("\uD83D\uDC65 Gerenciar clientes");
        button.setOnAction(event -> {
            ManageCustomersController controller = PageLoader.openPage("manage_customers.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.get("customerButtons").add(button);

        return buttons;
    }

    private HashMap<String, List<Button>> getAdministratorButtons(){
        HashMap<String, List<Button>> buttons = new HashMap<>();
        buttons.put("serviceButtons", new ArrayList<>());
        buttons.put("customerButtons", new ArrayList<>());
        buttons.put("userButtons", new ArrayList<>());
        buttons.put("workOrderButtons", new ArrayList<>());

        Button button = BigButtonComponent.create("\uD83D\uDCCA Gerar relatório");
        button.setOnAction(event -> {
            ReportController controller = PageLoader.openPage("report.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.get("serviceButtons").add(button);

        button = BigButtonComponent.create("\uD83D\uDC65 Gerenciar usuários");
        button.setOnAction(event -> {
            ManageUserController controller = PageLoader.openPage("manage_user.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.get("userButtons").add(button);

        button = BigButtonComponent.create("\uD83E\uDE9B Criar novo Serviço de Instalação");
        button.setOnAction(event -> {
            CreateInstallationServiceController controller = PageLoader.openPage("create_installation_service.fxml");
            controller.setLoggedUser(loggedUser);
        });
        buttons.get("serviceButtons").add(button);

        return buttons;
    }

    public void onLogOutClick() {
        PageLoader.openPage("login.fxml");
    }
}
