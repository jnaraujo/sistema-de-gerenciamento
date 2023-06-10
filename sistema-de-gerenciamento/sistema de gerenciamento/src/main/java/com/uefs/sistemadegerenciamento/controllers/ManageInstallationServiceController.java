package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.WorkOrderManagerApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.view.components.EmptyComponent;
import com.uefs.sistemadegerenciamento.view.components.InstallationServiceComponent;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.util.Comparator;
import java.util.List;

public class ManageInstallationServiceController extends Controller {
    private ObservableList<InstallationService> services;
    private final int COMPONENT_HEIGHT = 95 + 16;
    @FXML
    private VBox listVBox;

    @FXML
    private AnchorPane listAnchorPane;

    @Override
    public void setLoggedUser(User user) {
        super.setLoggedUser(user);
        services.addAll(fetchServices());
    }

    @FXML
    private void initialize() {
        WorkOrderManagerApplication.stage.setTitle("Gerenciar Serviços de Instalação");

        services = FXCollections.observableArrayList();

        listVBox.getChildren().add(EmptyComponent.create("Nenhum serviço cadastrado."));

        services.addListener(new ListChangeListener<InstallationService>() {
            @Override
            public void onChanged(Change<? extends InstallationService> change) {

                listVBox.getChildren().removeIf(node -> {
                    return node.getId().equals("empty-component");
                });

                while (change.next()) {
                    if (change.wasAdded()) {
                        for (InstallationService service : change.getAddedSubList()) {
                            addComponentToLayout(service);
                        }
                    } else if (change.wasRemoved()) {
                        for (InstallationService service : change.getRemoved()) {
                            removeComponentFromLayout(service);
                        }
                    }
                }

                if(listVBox.getChildren().size() == 0) {
                    listVBox.getChildren().add(EmptyComponent.create("Nenhum serviço cadastrado."));
                }

                listAnchorPane.setPrefHeight(COMPONENT_HEIGHT * listVBox.getChildren().size() + 16);
            }
        });
    }

    private void addComponentToLayout(InstallationService service) {
        listVBox.getChildren().add(InstallationServiceComponent.create(service, (event) -> {
            onUpdateComponentButtonClick(service);
        }, (event) -> {
            onDeleteComponentButtonClick(service);
        }));
    }

    private void removeComponentFromLayout(InstallationService service) {
        listVBox.getChildren().removeIf(node -> {
            return node.getId().equals("component-" + service.getId());
        });
    }

    private void onUpdateComponentButtonClick(InstallationService updatedService) {
        UpdateInstallationServiceController controller = PageLoader.openPage("update_installation_service.fxml");
        controller.setLoggedUser(getLoggedUser());
        controller.setInstallationService(updatedService);
        controller.setPreviousPage("manage_installation_service.fxml");
    }

    private void onDeleteComponentButtonClick(InstallationService deletedService) {
        if(getLoggedUser().getUserType() != UserType.ADMINISTRATOR) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Você não tem permissão para deletar Serviços de Instalação.");
            alert.setContentText("Apenas administradores podem deletar Serviços de Instalação.");
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
                alert.close();
            });
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletar Serviço de Instalação");
        alert.setHeaderText("Você tem certeza que deseja deletar o Serviço de Instalação " + deletedService.getDescription() + "?");
        alert.setContentText("Essa ação não pode ser desfeita.");
        alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
            alert.close();
        });

        ButtonType buttonTypeYes = new ButtonType("Sim, deletar Serviço de Instalação");
        ButtonType buttonTypeNo = new ButtonType("Não");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent((buttonType) -> {
            if(buttonType == buttonTypeYes) {
                deleteComponent(deletedService);
            }

            alert.close();
        });
    }

    private void deleteComponent(InstallationService service){
        services.remove(service);
        DAOManager.getInstallationServiceDao().delete(service.getId());
    }

    private List<InstallationService> fetchServices() {
        List<InstallationService> services = DAOManager.getInstallationServiceDao().getAll();
        services.sort(Comparator.comparing(InstallationService::getDescription));
        return services;
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }

    @FXML
    private void onAddServiceButtonClick(){
        CreateInstallationServiceController controller = PageLoader.openPage("create_installation_service.fxml");
        controller.setPreviousPage("manage_installation_service.fxml");
        controller.setLoggedUser(getLoggedUser());
    }
}
