package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import com.uefs.sistemadegerenciamento.view.components.UpdateUserComponent;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.List;

public class ManageUserController extends Controller {
    private ObservableList<User> users;

    private final int COMPONENT_HEIGHT = 95 + 16;

    @FXML
    private VBox userListVBox;

    @FXML
    private AnchorPane userListAnchorPane;

    @Override
    public void setLoggedUser(User user) {
        super.setLoggedUser(user);
        users.addAll(fetchUsers());
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Gerenciar Usuários");

        users = FXCollections.observableArrayList();

        users.addListener(new ListChangeListener<User>() {
            @Override
            public void onChanged(Change<? extends User> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (User user : change.getAddedSubList()) {
                            addUserToLayout(user, user.getId() == getLoggedUser().getId());
                        }
                    } else if (change.wasRemoved()) {
                        for (User user : change.getRemoved()) {
                            removeUserFromLayout(user);
                        }
                    }
                }

                userListAnchorPane.setPrefHeight(COMPONENT_HEIGHT * userListVBox.getChildren().size() + 16);
            }
        });
    }

    private void addUserToLayout(User user, boolean isButtonDisabled) {
        userListVBox.getChildren().add(UpdateUserComponent.create(user, isButtonDisabled, (event) -> {
            onUpdateUserButtonClick(user);
        }, (event) -> {
            onDeleteUserButtonClick(user);
        }));
    }

    private void removeUserFromLayout(User user) {
        userListVBox.getChildren().removeIf(node -> {
            return node.getId().equals("user-" + user.getId());
        });
    }

    private void onUpdateUserButtonClick(User updatedUser) {
        UpdateUserController updateUserController = (UpdateUserController) PageLoader.openPage("update_user.fxml");
        updateUserController.setLoggedUser(getLoggedUser());
        updateUserController.setUpdatedUser(updatedUser);
        updateUserController.setPreviousPage("manage_user.fxml");
    }

    private void onDeleteUserButtonClick(User deletedUser) {
        if(getLoggedUser().getUserType() != UserType.ADMINISTRATOR) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Você não tem permissão para deletar usuários");
            alert.setContentText("Apenas administradores podem deletar usuários");
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
                alert.close();
            });
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletar Usuário.");
        alert.setHeaderText("Você tem certeza que deseja deletar o usuário " + deletedUser.getName() + "?");
        alert.setContentText("Essa ação não pode ser desfeita.");
        alert.getDialogPane().getScene().getWindow().setOnCloseRequest((event) -> {
            alert.close();
        });

        ButtonType buttonTypeYes = new ButtonType("Sim, deletar usuário");
        ButtonType buttonTypeNo = new ButtonType("Não");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent((buttonType) -> {
            if(buttonType == buttonTypeYes) {
                deleteUser(deletedUser);
            }

            alert.close();
        });
    }

    private void deleteUser(User user){
        WorkOrder order = DAOManager.getWorkOrderDao().findOpenOrderByTechnicianId(user.getId());

        if(order != null){
            order.setTechnicianId(null);
            DAOManager.getWorkOrderDao().update(order);
        }

        DAOManager.getUserDao().delete(user.getId());
        users.remove(user);
    }

    private List<User> fetchUsers() {
        List<User> users = DAOManager.getUserDao().getAll();
        users.sort(Comparator.comparing(User::getName));
        return users;
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }

    @FXML
    private void onAddUserButtonClick(){
        CreateUserController controller = PageLoader.openPage("create_user.fxml");
        controller.setPreviousPage("manage_user.fxml");
        controller.setLoggedUser(getLoggedUser());
    }
}
