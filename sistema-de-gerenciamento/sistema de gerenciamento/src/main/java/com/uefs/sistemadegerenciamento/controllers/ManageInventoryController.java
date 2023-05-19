package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.view.components.ComputerComponentComponent;
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

public class ManageInventoryController extends Controller {
    private ObservableList<ComputerComponent> components;
    private final int COMPONENT_HEIGHT = 95 + 16;
    @FXML
    private VBox componentListVBox;

    @FXML
    private AnchorPane componentListAnchorPane;

    @Override
    public void setLoggedUser(User user) {
        super.setLoggedUser(user);
        components.addAll(fetchInventory());
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Gerenciar estoque");

        components = FXCollections.observableArrayList();

        componentListVBox.getChildren().add(EmptyComponent.create("Nenhuma peça cadastrada."));

        components.addListener(new ListChangeListener<ComputerComponent>() {
            @Override
            public void onChanged(Change<? extends ComputerComponent> change) {

                componentListVBox.getChildren().removeIf(node -> {
                    return node.getId().equals("empty-component");
                });

                while (change.next()) {
                    if (change.wasAdded()) {
                        for (ComputerComponent component : change.getAddedSubList()) {
                            addComponentToLayout(component);
                        }
                    } else if (change.wasRemoved()) {
                        for (ComputerComponent component : change.getRemoved()) {
                            removeComponentFromLayout(component);
                        }
                    }
                }

                if(componentListVBox.getChildren().size() == 0) {
                    componentListVBox.getChildren().add(EmptyComponent.create("Nenhuma peça cadastrada."));
                }

                componentListAnchorPane.setPrefHeight(COMPONENT_HEIGHT * componentListVBox.getChildren().size() + 16);
            }
        });
    }

    private void addComponentToLayout(ComputerComponent component) {
        componentListVBox.getChildren().add(ComputerComponentComponent.create(component, (event) -> {
            onUpdateComponentButtonClick(component);
        }, (event) -> {
            onDeleteComponentButtonClick(component);
        }));
    }

    private void removeComponentFromLayout(ComputerComponent component) {
        componentListVBox.getChildren().removeIf(node -> {
            return node.getId().equals("component-" + component.getId());
        });
    }

    private void onUpdateComponentButtonClick(ComputerComponent updatedComponent) {
        UpdateComponentController controller = PageLoader.openPage("update_component.fxml");
        controller.setLoggedUser(getLoggedUser());
        controller.setComputerComponent(updatedComponent);
        controller.setPreviousPage("manage_inventory.fxml");
    }

    private void onDeleteComponentButtonClick(ComputerComponent deletedComponent) {
        if(getLoggedUser().getUserType() != UserType.ADMINISTRATOR) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Você não tem permissão para deletar componentes");
            alert.setContentText("Apenas administradores podem deletar componentes.");
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
                alert.close();
            });
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletar componente");
        alert.setHeaderText("Você tem certeza que deseja deletar o componente " + deletedComponent.getName() + "?");
        alert.setContentText("Essa ação não pode ser desfeita.");
        alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
            alert.close();
        });

        ButtonType buttonTypeYes = new ButtonType("Sim, deletar componente");
        ButtonType buttonTypeNo = new ButtonType("Não");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent((buttonType) -> {
            if(buttonType == buttonTypeYes) {
                deleteComponent(deletedComponent);
            }

            alert.close();
        });
    }

    private void deleteComponent(ComputerComponent component){
        components.remove(component);
        DAOManager.getInventoryDao().delete(component.getId());
    }

    private List<ComputerComponent> fetchInventory() {
        List<ComputerComponent> components = DAOManager.getInventoryDao().getAll();
        components.sort(Comparator.comparing(ComputerComponent::getName));
        return components;
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }
}
