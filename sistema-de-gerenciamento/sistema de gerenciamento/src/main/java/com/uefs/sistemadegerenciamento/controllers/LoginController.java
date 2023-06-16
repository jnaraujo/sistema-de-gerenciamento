package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.WorkOrderManagerApplication;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.DaoMock;
import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController extends Controller {
    @FXML
    private Label titleText;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void initialize() {
        WorkOrderManagerApplication.stage.setTitle("Login - Sistema de Gerenciamento");
    }

    @FXML
    private void onLoginClick()  {
        emailField.styleProperty().setValue("");
        passwordField.styleProperty().setValue("");

        if(emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            if(emailField.getText().isEmpty())
                emailField.styleProperty().setValue("-fx-border-color: red");
            if(passwordField.getText().isEmpty())
                passwordField.styleProperty().setValue("-fx-border-color: red");

            titleText.setText("Preencha todos os campos");
            return;
        }

        User user = DAOManager.getUserDao().findByEmail(emailField.getText());

        if(user == null) {
            titleText.setText("Usuário não encontrado");
            return;
        }

        if(!user.getPassword().equals(passwordField.getText())) {
            titleText.setText("Senha incorreta");
            return;
        }

        this.openPage("home.fxml", user);
    }

    private void openPage(String url, User user) {
        HomeController homeController = PageLoader.openPage(url);
        homeController.setLoggedUser(user);
    }

    @FXML
    private void onMockClick() {
        DaoMock daoMock = new DaoMock();
        daoMock.mock();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dados de Exemplo");
        alert.setHeaderText("Dados de exemplo inseridos com sucesso!");

        alert.showAndWait();
    }

    @FXML
    private void deleteAllData(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletar todos os dados");
        alert.setHeaderText("Tem certeza que deseja deletar todos os dados?");
        alert.setContentText("Essa ação não pode ser desfeita.");

        if(alert.showAndWait().get() == ButtonType.OK){
            DAOManager.getWorkOrderDao().deleteAll();
            DAOManager.getUserDao().deleteAll();
            DAOManager.getInventoryDao().deleteAll();
            DAOManager.getCleaningServiceDao().deleteAll();
            DAOManager.getInstallationServiceDao().deleteAll();
            DAOManager.getCustomerDao().deleteAll();

            if(DAOManager.getUserDao().findByEmail("admin") == null) {
                DAOManager.getUserDao().save(
                        new Administrator(
                                "ADMIN",
                                "admin",
                                "admin"
                        )
                );
            }
        }
    }
}
