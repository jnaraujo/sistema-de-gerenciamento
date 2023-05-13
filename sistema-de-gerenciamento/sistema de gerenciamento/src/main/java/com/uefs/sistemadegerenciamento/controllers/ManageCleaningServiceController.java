package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.view.components.CleaningServiceComponent;
import com.uefs.sistemadegerenciamento.view.components.EmptyComponent;
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

public class ManageCleaningServiceController {
    private User loggedUser;
    private ObservableList<CleaningService> services;
    private final int COMPONENT_HEIGHT = 95 + 16;
    @FXML
    private VBox listVBox;

    @FXML
    private AnchorPane listAnchorPane;

    public void setLoggedUser(User user) {
        this.loggedUser = user;
        services.addAll(fetchServices());
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Gerenciar Serviços de Limpeza");

        services = FXCollections.observableArrayList();

        listVBox.getChildren().add(EmptyComponent.create("Nenhum serviço cadastrado."));

        services.addListener(new ListChangeListener<CleaningService>() {
            @Override
            public void onChanged(Change<? extends CleaningService> change) {

                listVBox.getChildren().removeIf(node -> {
                    return node.getId().equals("empty-component");
                });

                while (change.next()) {
                    if (change.wasAdded()) {
                        for (CleaningService service : change.getAddedSubList()) {
                            addComponentToLayout(service);
                        }
                    } else if (change.wasRemoved()) {
                        for (CleaningService service : change.getRemoved()) {
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

    private void addComponentToLayout(CleaningService service) {
        listVBox.getChildren().add(CleaningServiceComponent.create(service, (event) -> {
            onUpdateComponentButtonClick(service);
        }, (event) -> {
            onDeleteComponentButtonClick(service);
        }));
    }

    private void removeComponentFromLayout(CleaningService service) {
        listVBox.getChildren().removeIf(node -> {
            return node.getId().equals("component-" + service.getId());
        });
    }

    private void onUpdateComponentButtonClick(CleaningService updatedService) {
        UpdateCleaningServiceController controller = PageLoader.openPage("update_cleaning_service.fxml");
        controller.setLoggedUser(loggedUser);
        controller.setService(updatedService);
    }

    private void onDeleteComponentButtonClick(CleaningService deletedService) {
        if(loggedUser.getUserType() != UserType.ADMINISTRATOR) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Você não tem permissão para deletar Serviços de Limpeza.");
            alert.setContentText("Apenas administradores podem deletar Serviços de Limpeza.");
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
                alert.close();
            });
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletar Serviço de Instalação");
        alert.setHeaderText("Você tem certeza que deseja deletar o Serviço de Limpeza?");
        alert.setContentText("Essa ação não pode ser desfeita.");
        alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
            alert.close();
        });

        ButtonType buttonTypeYes = new ButtonType("Sim, deletar Serviço de Limpeza");
        ButtonType buttonTypeNo = new ButtonType("Não");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent((buttonType) -> {
            if(buttonType == buttonTypeYes) {
                deleteComponent(deletedService);
            }

            alert.close();
        });
    }

    private void deleteComponent(CleaningService service){
        services.remove(service);
        DAOManager.getCleaningServiceDao().delete(service.getId());
    }

    private List<CleaningService> fetchServices() {
        List<CleaningService> services = DAOManager.getCleaningServiceDao().getAll();
        return services;
    }

    @FXML
    private void onBackButtonClick() {
        PageLoader.goHome(loggedUser);
    }
}
