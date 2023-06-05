package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.errors.InvalidSatisfactionScoreException;
import com.uefs.sistemadegerenciamento.errors.ServiceOrderWithoutTechnicianException;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.Formatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateWorkOrderSalesController extends Controller {
    private WorkOrder workOrder;

    @FXML
    private Label infoLabel;

    @FXML
    private Label technicianLabel;

    @FXML
    private Label customerLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label createdDateLabel;

    @FXML
    private Label finishedDateLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label costLabel;

    @FXML
    private TextField paymentMethodTextField;

    @FXML
    private TextField customerSatisfactionTextField;


    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;

        paymentMethodTextField.setText(workOrder.getPaymentMethod() != null ? workOrder.getPaymentMethod() : "");
        customerSatisfactionTextField.setText(String.valueOf(workOrder.getSatisfactionScore()));
        String technicianName = workOrder.getTechnicianId() != null ? DAOManager.getUserDao().findById(workOrder.getTechnicianId()).getName() : "Não atribuído";
        technicianLabel.setText("Técnico: " + technicianName);
        customerLabel.setText("Cliente: " + DAOManager.getCustomerDao().findById(workOrder.getCustomerId()).getName());
        createdDateLabel.setText("Data de criação: " + Formatter.date(workOrder.getCreatedAt()));
        statusLabel.setText("Status: " + workOrder.getStatus().toString());
        finishedDateLabel.setText("Data de finalização: " + (workOrder.getFinishedAt() != null ? Formatter.date(workOrder.getFinishedAt()) : "Não finalizado"));
        priceLabel.setText("Preço: " + Formatter.currency(workOrder.getPrice()));
        costLabel.setText("Custo: " + Formatter.currency(workOrder.getCost()));
    }

    @FXML
    private void initialize() {
        HelloApplication.stage.setTitle("Finalização da Venda");
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }
    @FXML
    private void onUpdateButtonClick() throws ServiceOrderWithoutTechnicianException {
        if(!canUserUpdateOrder(getLoggedUser())){
            error("Você não tem permissão para atualizar esta ordem de serviço.");
            return;
        }

        if(!checkInputs()) return;
        DAOManager.getWorkOrderDao().update(workOrder);
        success("Ordem de serviço atualizada com sucesso.");
    }

    private boolean canUserUpdateOrder(User user){
        boolean isLoggedUserReceptionist = getLoggedUser().getUserType().equals(UserType.RECEPTIONIST);
        boolean isUserAdministrator = user.getUserType().equals(UserType.ADMINISTRATOR);

        return isLoggedUserReceptionist || isUserAdministrator;
    }

    private boolean checkInputs() {
        paymentMethodTextField.getStyleClass().remove("error");
        customerSatisfactionTextField.getStyleClass().remove("error");

        if(paymentMethodTextField.getText().isEmpty()){
            error("O campo 'Forma de pagamento' é obrigatório.");
            paymentMethodTextField.getStyleClass().add("error");
            return false;
        }

        if(paymentMethodTextField.getText().isEmpty()){
            error("O campo 'Forma de pagamento' é obrigatório.");
            paymentMethodTextField.getStyleClass().add("error");
            return false;
        }

        if(paymentMethodTextField.getText().isEmpty() || customerSatisfactionTextField.getText().isEmpty()){
            error("O campo 'Satisfação do cliente' é obrigatório.");
            customerSatisfactionTextField.getStyleClass().add("error");
            return false;
        }

        if(!customerSatisfactionTextField.getText().matches("[0-9]+")){
            error("O campo 'Satisfação do cliente' deve conter apenas números.");
            customerSatisfactionTextField.getStyleClass().add("error");
            return false;
        }

        int satisfactionScore = Integer.parseInt(customerSatisfactionTextField.getText());

        if(satisfactionScore < 0 || satisfactionScore > 5){
            error("O campo 'Satisfação do cliente' deve conter um valor entre 0 e 5.");
            customerSatisfactionTextField.getStyleClass().add("error");
            return false;
        }

        workOrder.setPaymentMethod(paymentMethodTextField.getText());
        try {
            workOrder.setSatisfactionScore(satisfactionScore);
        } catch (InvalidSatisfactionScoreException e) {
            customerSatisfactionTextField.getStyleClass().add("error");
            error("O campo 'Satisfação do cliente' deve conter um valor entre 0 e 5.");
            return false;
        }

        DAOManager.getWorkOrderDao().update(workOrder);

        success("Ordem de serviço atualizada com sucesso.");

        return true;
    }

    private void info(String message) {
        infoLabel.setStyle("-fx-text-fill: #000000");
        infoLabel.setText(message);
    }

    private void error(String message) {
        infoLabel.setStyle("-fx-text-fill: #ff0000");
        infoLabel.setText(message);
    }

    private void success(String message) {
        infoLabel.setStyle("-fx-text-fill: #157315");
        infoLabel.setText(message);
    }
}
